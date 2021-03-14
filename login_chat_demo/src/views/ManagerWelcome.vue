<template>
  <div id="managerWelcome">
    <div>
      欢迎管理员登录！
      <label>{{managerToken}}</label><br/>
      <label>{{managerId}}</label>
    </div>
    <div>
      <el-button v-on:click="managerLogout">退出登录</el-button>
    </div>
    <div>
      <a href="/managerInfoVue">查看管理员信息</a>
    </div>
    <div>
      <a href="/welcome">首页</a>
    </div>
    <div>
      <a href="/chatVue">聊天</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "ManagerWelcome",
  created() {
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
      this.$messageBox.confirm('此操作将退出管理员登录，是否继续？','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        this.postRequest('/backendServer/logout').then(resp=>{
          window.sessionStorage.removeItem("managerToken");
          window.sessionStorage.removeItem("managerID");
          window.sessionStorage.removeItem('username');
          window.sessionStorage.removeItem('loginRole');
          this.$router.replace('/');
        })
      }).catch(()=>{
        this.$message.info('已取消退出!');
      })
    }
  }
}
</script>

<style scoped>

</style>