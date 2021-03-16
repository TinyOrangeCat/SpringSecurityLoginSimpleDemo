<template>
  <div id="managerInfoVue">
    <div>
      <table>
        <thead>
          <tr>
<!--            <td>管理员账户ID</td>
            <td>管理员账户</td>
            <td>管理员密码</td>
            <td>管理员角色ID</td>-->
            <td>{{ $t('managerTable.managerAccountId') }}</td>
            <td>{{ $t('managerTable.managerAccount') }}</td>
            <td>{{ $t('managerTable.managerPassword') }}</td>
            <td>{{ $t('managerTable.managerRoleId') }}</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>{{ manager.managerId }}</td>
            <td>{{ manager.managerAccount }}</td>
            <td>{{ manager.managerPassword }}</td>
            <td>{{ manager.managerRoleId }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div>
      <table>
        <thead>
        <tr>
<!--          <td>管理员角色ID</td>
          <td>管理员角色名称</td>-->
          <td>{{ $t('managerTable.managerRoleId') }}</td>
          <td>{{ $t('managerTable.managerRoleName') }}</td>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>{{ managerRole.roleId }}</td>
          <td>{{ managerRole.roleName }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div>
      <table>
        <thead>
        <tr>
<!--          <td>管理员账户信息ID</td>
          <td>管理员账户ID</td>
          <td>管理员名字</td>
          <td>管理员工号</td>
          <td>管理员邮箱</td>-->
          <td>{{ $t('managerTable.managerInfoId') }}</td>
          <td>{{ $t('managerTable.managerAccountId') }}</td>
          <td>{{ $t('managerTable.managerName') }}</td>
          <td>{{ $t('managerTable.managerWorkId') }}</td>
          <td>{{ $t('managerTable.managerEmail') }}</td>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>{{ managerInfo.managerInfoId }}</td>
          <td>{{ managerInfo.managerAccountId }}</td>
          <td>{{ managerInfo.managerName }}</td>
          <td>{{ managerInfo.managerCode }}</td>
          <td>{{ managerInfo.managerEmail }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div>
      <a href="/welcome">{{ $t('uiText.homePage') }}</a>
    </div>
    <div>
      <el-button v-on:click="managerLogout">{{ $t('loginUI.logoutButton') }}</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "ManagerInfoVue",
  created() {
    console.log('managerInfoVue '+this.$route.name)
    this.$emit('fromRouterView',this.$route.name)
    this.getRequest('/backendServer/manager/getManagerInfo', {'managerId':window.sessionStorage.getItem('managerID')}).then(resp=>{
      if(resp.obj){
        if(resp.obj.manager){
          const manager = resp.obj.manager;
          this.manager.managerId = manager.managerId;
          this.manager.managerAccount = manager.managerAccount;
          this.manager.managerPassword = manager.managerPassword;
          this.manager.managerRoleId = manager.managerRoleId;
        }
        if(resp.obj.managerRole){
          const role = resp.obj.managerRole;
          this.managerRole.roleId = role.roleId;
          this.managerRole.roleName = role.roleName;
        }
        if(resp.obj.managerInfo){
          const managerInfo = resp.obj.managerInfo;
          this.managerInfo.managerInfoId = managerInfo.managerInfoId;
          this.managerInfo.managerAccountId = managerInfo.managerAccountId;
          this.managerInfo.managerCode = managerInfo.managerName;
          this.managerInfo.managerEmail = managerInfo.managerCode;
          this.managerInfo.managerName = managerInfo.managerEmail;
        }
      }
    })
  },
  data(){
    return{
      manager:{
        managerId: '',
        managerAccount: '',
        managerPassword: '',
        managerRoleId: ''
      },
      managerRole:{
        roleId: '',
        roleName: ''
      },
      managerInfo:{
        managerInfoId: '',
        managerAccountId: '',
        managerName: '',
        managerCode: '',
        managerEmail: ''
      }
    }
  },
  methods:{
    managerLogout(){
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