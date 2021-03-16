<template>
  <div id="managerWelcome">
    <div>
<!--      欢迎管理员登录！-->
      {{ $t('uiText.welcomeManager') }}
      <label>{{managerToken}}</label><br/>
      <label>{{managerId}}</label>
    </div>
    <div>
      <!-- 退出登录     -->
      <el-button v-on:click="managerLogout">{{ $t('loginUI.logoutButton') }}</el-button>
    </div>
    <div>
      <!--  查看管理员信息    -->
      <a href="/managerInfoVue">{{ $t('uiText.managerPersonInfo') }}</a>
    </div>
    <div>
      <a href="/welcome">{{ $t('uiText.homePage') }}</a>
      <!--   首页   -->
    </div>
    <div>
      <a href="/chatVue">{{ $t('uiText.chat') }}</a>
      <!--   聊天   -->
    </div>
  </div>
</template>

<script>
export default {
  name: "ManagerWelcome",
  created() {
    console.log('managerWelcome '+this.$route.name)
    this.$emit('fromRouterView',this.$route.name)
    //连接websocket
    //this.$store.dispatch('connectChatWebsocket');

    this.managerToken = this.$store.getters.getManagerTokenInfo;
    this.managerId = this.$store.getters.getManagerAccountId;
  },
  data(){
    return{
      managerToken: '',
      managerId: -1
    }
  },
  methods:{
    managerLogout(){
      this.$messageBox.confirm(this.$t('sysMessage.logoutConfirm'),this.$t('el.messagebox.title'),{
        //'确定'
        confirmButtonText: this.$t('el.popconfirm.confirmButtonText'),
        cancelButtonText: this.$t('el.popconfirm.cancelButtonText'),
        type: 'warning'
      }).then(()=>{
        this.postRequest('/backendServer/logout').then(resp=>{
          window.sessionStorage.removeItem("managerToken");
          window.sessionStorage.removeItem("managerID");
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

</style>