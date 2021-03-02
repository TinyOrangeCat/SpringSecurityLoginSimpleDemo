<template>
  <div id="userWelcome">
    <div id="userInnerDiv">
      <div>
        欢迎用户！
        <label>{{userToken}}</label><br/>
        <label>{{userId}}</label>
      </div>
      <div>
        <el-button v-on:click="userLogout">退出登录</el-button>
      </div>
      <div>
        <a href="/userInfoVue">查看个人信息</a>
      </div>
      <div>
        <a href="/welcome">首页</a>
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
      this.$messageBox.confirm('此操作将退出登录，是否继续？','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        this.postRequest('/backendServer/logout').then(resp=>{
          window.sessionStorage.removeItem("userToken");
          window.sessionStorage.removeItem("userID");
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

  #userInnerDiv{
    margin: 50px auto 0px auto;
    width: 80%;
  }
</style>