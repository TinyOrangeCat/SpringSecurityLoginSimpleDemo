<template>
  <div id="userInfoVue">
    <div>
      <table>
        <thead>
          <tr>
<!--            <td>用户信息ID</td>
            <td>用户账户ID</td>
            <td>用户名字</td>
            <td>用户年龄</td>
            <td>用户性别</td>
            <td>用户邮箱</td>-->
            <td>{{ $t('userTable.userInfoId') }}</td>
            <td>{{ $t('userTable.userAccountId') }}</td>
            <td>{{ $t('userTable.userName') }}</td>
            <td>{{ $t('userTable.userAge') }}</td>
            <td>{{ $t('userTable.userGender') }}</td>
            <td>{{ $t('userTable.userEmail') }}</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>{{userInfo.userInfoId}}</td>
            <td>{{userInfo.userAccountId}}</td>
            <td>{{userInfo.userName}}</td>
            <td>{{userInfo.userAge}}</td>
            <td>{{userInfo.userGender}}</td>
            <td>{{userInfo.userEmail}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div>
      <table>
        <thead>
        <tr>
<!--          <td>角色ID</td>
          <td>角色名字</td>-->
          <td>{{ $t('userTable.userRoleId') }}</td>
          <td>{{ $t('userTable.userRoleName') }}</td>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>{{role.roleId}}</td>
          <td>{{role.roleName}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div>
      <table>
        <thead>
        <tr>
<!--          <td>账户ID</td>
          <td>账户登录名</td>
          <td>账户登录密码</td>
          <td>账户角色ID</td>-->
          <td>{{ $t('userTable.userAccountId') }}</td>
          <td>{{ $t('userTable.userAccount') }}</td>
          <td>{{ $t('userTable.userPassword') }}</td>
          <td>{{ $t('userTable.userRoleId') }}</td>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>{{user.userId}}</td>
          <td>{{user.userAccount}}</td>
          <td>{{user.userPassword}}</td>
          <td>{{user.userRoleId}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div>
      <a href="/welcome">{{ $t('uiText.homePage') }}</a>
    </div>
    <div>
      <el-button v-on:click="userLogout">{{ $t('loginUI.logoutButton') }}</el-button>
    </div>
    <div>
      token:
      <label>{{tokenString}}</label><br/>
      userId:
      <label>{{userId}}</label><br/>
    </div>
  </div>
</template>

<script>
import {Message} from "element-ui";

export default {
name: "UserInfoVue",
  created() {
    console.log('userInfoVue '+this.$route.name)
    this.$emit('fromRouterView',this.$route.name)
    //初始化数据
    this.userId = this.$store.getters.getUserAccountId;
    this.tokenString = this.$store.getters.getUserTokenInfo;
    this.getRequest('/backendServer/user/getUserInfo', {'userId':window.sessionStorage.getItem('userID')}).then(resp=>{
      if(resp.obj){
        if(resp.obj.userInfo){
          const userInfo = resp.obj.userInfo;
          this.userInfo.userInfoId = userInfo.userInfoId;
          this.userInfo.accountId = userInfo.userAccountId;
          this.userInfo.userName = userInfo.userName;
          this.userInfo.userAge = userInfo.userAge;
          this.userInfo.userGender = userInfo.userGender;
          this.userInfo.userEmail = userInfo.userEmail;
        }
        if(resp.obj.role){
          const role = resp.obj.role;
          this.role.roleId = role.roleId;
          this.role.roleName = role.roleName;
        }
        if(resp.obj.account){
          const account = resp.obj.account;
          this.user.userId = account.userId;
          this.user.userAccount = account.userAccount;
          this.user.userPassword = account.userPassword;
        }
      }
    })
  },
  data(){
    return{
      userId: -1,
      tokenString: '',
      userInfo:{
        userInfoId: '',
        userAccountId: '',
        userName: '',
        userAge: '',
        userGender: '',
        userEmail: ''
      },
      role:{
        roleId: '',
        roleName: ''
      },
      user:{
        userId: '',
        userAccount: '',
        userPassword: '',
        userRoleId: ''
      }
    }
  },
  methods:{
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

</style>