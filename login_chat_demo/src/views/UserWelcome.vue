<template>
  <div id="userWelcome">
    <div id="userInnerDiv">
      <div>
        <!--        欢迎用户！-->
        {{ $t('uiText.welcomeUser') }}
        <label>{{userToken}}</label><br/>
        <label>{{userId}}</label>
      </div>
      <div>
        <!--        退出登录-->
        <el-button v-on:click="userLogout">{{ $t('loginUI.logoutButton') }}</el-button>
      </div>
      <div>
        <a href="/userInfoVue">{{ $t('uiText.userPersonInfo') }}</a>
      </div>
      <div>
        <a href="/welcome">{{ $t('uiText.homePage') }}</a>
      </div>
      <div>
        <a href="/chatVue">{{ $t('uiText.chat') }}</a>
      </div>
      <div>
        <el-button v-on:click="requestTest">UserRequestTest</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
name: "UserWelcome",
  created() {
    console.log('userWelcome '+this.$route.name)
    this.$emit('fromRouterView',this.$route.name)
    //连接websocket
    //this.$store.dispatch('connectChatWebsocket');

    this.userToken = this.$store.getters.getUserTokenInfo;
    this.userId = this.$store.getters.getUserAccountId;
  },
  data(){
    return{
      userToken: '',
      userId: -1
    }
  },
  methods:{
    requestTest(){
      this.putRequest("/backendServer/user/meowmewo",{'a':'1111'}).then(resp=>{
        console.log("putTest: "+resp);
      })
    },
    userLogout(){
      this.$messageBox.confirm(this.$t('sysMessage.logoutConfirm'),this.$t('el.messagebox.title'),{
        confirmButtonText: this.$t('el.popconfirm.confirmButtonText'),
        cancelButtonText: this.$t('el.popconfirm.cancelButtonText'),
        type: 'warning'
      }).then(()=>{
        this.postRequest('/backendServer/logout').then(resp=>{
          window.sessionStorage.removeItem("userToken");
          window.sessionStorage.removeItem("userID");
          window.sessionStorage.removeItem('username');
          window.sessionStorage.removeItem('loginRole');
          window.sessionStorage.removeItem('lang');
          this.$router.replace('/');
        })
      }).catch(()=>{
        this.$message.info(this.$t('sysMessage.cancelLogout'));
      })
    }
  }

}
</script>

<style scoped>

  #userInnerDiv{
    margin: 50px auto 0px auto;
    width: 80%;
  }
</style>