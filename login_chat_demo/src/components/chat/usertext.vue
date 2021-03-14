<template>
  <div id="uesrtext">
    <textarea placeholder="按 Ctrl + Enter 发送" v-model="content" v-on:keyup="addMessage"></textarea>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'uesrtext',
  created() {
    if(window.sessionStorage.getItem('loginRole') === 'roleManager'){
      this.isManager = true;
    }
  },
  computed: mapState([
    'userCurrentObj',
    'managerCurrentObj'
  ]),
  data () {
    return {
      content:'',
      isManager: false,
    }
  },
  methods: {
  	addMessage (e) {
  		if (e.ctrlKey && e.keyCode ===13 && this.content.length) {
        if(this.userCurrentObj == null && this.managerCurrentObj==null){
          console.log('ChatVue no user is selected,do not send message!!!');
          return
        }
        /*this.$store.commit('addMessage',this.content);
        this.content='';*/
        let msgObj = new Object();
        if(this.userCurrentObj != null){
          msgObj.to = this.userCurrentObj.userAccount;
        }else {
          msgObj.to = this.managerCurrentObj.managerAccount;
        }
        msgObj.messageContent = this.content;
  			this.$store.state.stomp.send('/ws/chat',{},JSON.stringify(msgObj));
        this.$store.commit('addMessage',msgObj);
        this.content='';
  		}
  	}
  }
}
</script>

<style lang="scss" scoped>
#uesrtext {
	position: absolute;
  bottom: 0;
  right: 0;
  width: 100%;
  height: 30%;
  border-top: solid 1px #DDD;
  > textarea {
  	padding: 10px;
  	width: 100%;
  	height: 100%;
  	border: none;
  	outline: none;
  }
}
</style>
