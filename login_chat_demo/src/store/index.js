/*
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
*/
import Vue from 'vue'
import Vuex from 'vuex'
import {getRequest} from '../utils/api'
import SocketJS from 'sockjs-client'
import Stomp from 'stompjs'
import { Notification } from 'element-ui'
import {$t} from '../main'
import {$tc} from '../main'

Vue.use(Vuex)

/*export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})*/

const now = new Date();

const store = new Vuex.Store({
      state: {
        userTokenInfo: '',
        userAccountId: -1,
        managerTokenInfo: '',
        managerAccountId: -1,
        imagesPaths: [
          require('../assets/images/1.jpg'),
          require('../assets/images/2.png'),
          require('../assets/images/3.jpg'),
          require('../assets/images/1.jpg'),
          require('../assets/images/2.png'),
          require('../assets/images/3.jpg'),
          /*'./src/assets/images/1.jpg',
          './src/assets/images/2.png',
          './src/assets/images/3.jpg',
          './src/assets/images/1.jpg',
          './src/assets/images/2.png',
          './src/assets/images/3.jpg'*/
        ],
        /*sessions:[{
          id:1,
          user:{
            name:'示例介绍',
            img:'./src/assets/images/2.png'
          },
          messages:[{
            content:'Hello，这是一个基于Vue + Vuex + Webpack构建的简单chat示例，聊天记录保存在localStorge, 有什么问题可以通过Github Issue问我。',
            date:now
          },{
            content:'项目地址(原作者): https://github.com/coffcer/vue-chat',
            date:now
          },{
            content:'本项目地址(重构): https://github.com/is-liyiwei',
            date:now
          }]
        },{
          id:2,
          user:{
            name:'webpack',
            img:'./src/assets/images/3.jpg'
          },
          messages:[{
            content:'Hi，我是webpack哦',
            date:now
          }]
        }],*/
        sessions: {},
        currentSessionId:-1,
        managerCurrentObj: null,
        userCurrentObj: null,
        filterKey:'',
        dbManagers: [],
        dbUsers: [],
        stomp: null,//use for websocket
        needDot: {}
      },
      mutations: {//this.$store.commit('setTokenInfo',payload)
        /*setTokenInfo(state,payload){
          state.tokenInfo = payload;
        }*/
        setUserTokenInfo: (state,payload) => {
          state.userTokenInfo = payload;
        },
        setAccountId: (state,payload) => {
          state.userAccountId = payload;
        },
        setManagerTokenInfo: (state,payload) => {
          state.managerTokenInfo = payload;
        },
        setManagerAccountId: (state,payload) => {
          state.managerAccountId = payload;
        },

        changeCurrentSessionId (state,id) {
          state.currentSessionId = id;
        },
        changeManagerCurrentObject (state,obj) {
          state.managerCurrentObj = obj;
          state.userCurrentObj = null;
          //已读消息，取消红点提示
          Vue.set(state.needDot,window.sessionStorage.getItem('username')+'#'+state.managerCurrentObj.managerAccount,false);
        },
        changeUserCurrentObject (state,obj) {
          state.userCurrentObj = obj;
          state.managerCurrentObj = null;
          //已读消息，取消红点提示
          Vue.set(state.needDot,window.sessionStorage.getItem('username')+'#'+state.userCurrentObj.userAccount,false);
        },
        addMessage (state,msg) {
          let currentUsername = window.sessionStorage.getItem('username');
          let mss = state.sessions[currentUsername+'#'+msg.to];
          if(!mss){
            //state.sessions[currentUsername+'#'+msg.to] = [];
            //设置Vue监听sessions，实现实时刷新对话
            Vue.set(state.sessions,currentUsername+'#'+msg.to,[]);
          }
          state.sessions[currentUsername+'#'+msg.to].push({
            content: msg.messageContent,
            date: new Date(),
            self: !msg.notSelf//判定是否是自己
          })
          /*state.sessions[state.currentSessionId-1].messages.push({
            content:msg,
            date: new Date(),
            self:true
          })*/
        },
        INIT_DATA (state) {
          //浏览器本地聊天记录
          let data = localStorage.getItem('vue-chat-session');
          //console.log(data)
          if (data) {
            state.sessions = JSON.parse(data);
          }
        },
        INIT_DB_USER(state,data){
          state.dbUsers = data;
        },
        INIT_DB_MANAGER(state,data){
          state.dbManagers = data;
        }
      },
      actions: {//this.$store.dispatch('setTokenInfo',payload)
        connectChatWebsocket(context){
          console.log('connectChatWebsocket');
          //context.state.stomp = Stomp.overWS('ws://localhost:8081/ws/chatEndpoint');//连接端点
          //context.state.stomp = Stomp.client('ws://localhost:8081/ws/chatEndpoint');//连接端点
          context.state.stomp = Stomp.over(new SocketJS('/ws/chatEndpoint'));//连接端点

          const role = window.sessionStorage.getItem('loginRole');
          console.log('connectChatWebsocket role = '+role);
          let accountToken = '';
          if(role === 'roleUser'){
            accountToken = window.sessionStorage.getItem('userToken');
          }else if(role === 'roleManager'){
            accountToken = window.sessionStorage.getItem('managerToken');
          }
          console.log('ready to connect websocket');
          context.state.stomp.connect({'Auth-Token':accountToken},success=>{
            //订阅消息
            context.state.stomp.subscribe('/user/queue/chat',msg=>{
              //消息体
              let receiveMessage = JSON.parse(msg.body);
              console.log(receiveMessage);
              //接收消息
              //消息提示
              if((role === 'roleUser' && (!context.state.managerCurrentObj || receiveMessage.from != context.state.managerCurrentObj.managerAccount))
                || (role === 'roleManager' && ((!context.state.managerCurrentObj && !context.state.userCurrentObj)
                  || (context.state.managerCurrentObj && receiveMessage.from != context.state.managerCurrentObj.managerAccount)
                  || (context.state.userCurrentObj && receiveMessage.from != context.state.userCurrentObj.userAccount)))){
                Notification({
                  //title: '[ '+receiveMessage.fromNickName+' ]发来了一条消息!',
                  title: '[ '+receiveMessage.fromNickName+' ]'+$tc('sysMessage.userSendMessage'),
                  message: receiveMessage.messageContent.length>10 ? receiveMessage.messageContent.substr(0,10)+'......' : receiveMessage.messageContent,
                  position: 'bottom-right'
                });
                //设置红点，提示未读信息
                Vue.set(context.state.needDot,window.sessionStorage.getItem('username')+'#'+receiveMessage.from,true);
              }
              receiveMessage.notSelf = true;
              receiveMessage.to = receiveMessage.from;
              context.commit('addMessage',receiveMessage);
            })
          },error=>{
            console.log('websocket connect error.');
          });
        },
        //不同身份不同请求url
        initData (context) {
          context.commit('INIT_DATA');//加载记录中的聊天记录
          let role = window.sessionStorage.getItem('loginRole');
          let requestUrl = '/backendServer';
          if(role === 'roleUser'){
            requestUrl = requestUrl+'/user/chat/getAllManagers?currentUser='+window.sessionStorage.getItem('username');
          }else if(role === 'roleManager'){
            requestUrl = requestUrl+'/manager/chat/managerGetUserManagers?currentUser='+window.sessionStorage.getItem('username');
          }
          getRequest(requestUrl).then(resp=>{
            if(resp){
              if(role === 'roleManager'){
                context.commit('INIT_DB_USER',resp.obj.allUser);
              }
              context.commit('INIT_DB_MANAGER',resp.obj.allManager);
            }
          })
        }
      },
      getters: {//this.$store.getters.getTokenInfo
        getUserTokenInfo: state => {
          return state.userTokenInfo;
        },
        getManagerTokenInfo: state => {
          return state.managerTokenInfo;
        },
        getManagerAccountId: state=>{
          return state.managerAccountId;
        },
        getUserAccountId: state=>{
          return state.userAccountId;
        },
        getImgUrl: state => {
          return function (index) {
            return state.imagesPaths[index];
          }
        },
        isStompEmpty: state => {
          return state.stomp==null;
        },

      }
    }
)

store.watch(function (state) {
  return state.sessions
},function (val) {
  console.log('CHANGE: ', val);
  localStorage.setItem('vue-chat-session', JSON.stringify(val));
},{
  deep:true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})

export default store

