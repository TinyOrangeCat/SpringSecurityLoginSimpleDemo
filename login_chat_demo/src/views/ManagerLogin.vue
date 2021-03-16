<template>
  <div id="managerLoginDiv">
    <div id="loginDiv" v-loading="isLoading" :element-loading-text="$t('loginUI.loginIng')">
      <h3 class="loginTitle">{{ $t('uiText.managerLoginTitle') }}</h3>
      <el-form :label-position="labelPosition" :model="loginForm" :rules="loginFormRules" ref="userLoginForm" label-width="100px" class="demo-ruleForm">
        <el-form-item :label="$t('loginUI.loginAccount')" prop="loginAccount">
          <el-input v-model="loginForm.loginAccount" :placeholder="$t('loginUI.loginAccountInfo')"></el-input>
        </el-form-item>
        <el-form-item :label="$t('loginUI.loginPassword')" prop="loginPassword">
          <el-input v-model="loginForm.loginPassword" show-password :placeholder="$t('loginUI.loginPasswordInfo')"></el-input>
        </el-form-item>
        <el-form-item prop="loginCode">
          <el-input id="loginCodeInput" v-model="loginForm.loginCode" :placeholder="$t('loginUI.loginVerifyCodeInfo')"></el-input>
          <el-image id="codeImage" v-bind:src="captchaUrl" :alt="$t('el.image.error')" v-on:click="getCaptchaImage" style="cursor: pointer"></el-image>
        </el-form-item>
        <!--        <el-form-item label="记住登录?">
                  <el-checkbox v-model="ruleForm.rememberMe"></el-checkbox>
                </el-form-item>-->
        <el-form-item>
          <el-button type="primary" @click="submitForm('userLoginForm')">{{ $t('loginUI.loginButton') }}</el-button>
          <el-button @click="resetForm('userLoginForm')">{{ $t('loginUI.loginReset') }}</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
export default {
  name: "ManagerLogin",
  created() {
    console.log('managerLogin '+this.$route.name)
    this.$emit('fromRouterView',this.$route.name)
  },
  destroyed() {
    this.isLoading = false;
  },
  data() {
    return {
      labelPosition: 'left',
      isLoading: false,
      captchaUrl: '/backendServer/getCaptcha?time='+new Date,
      loginForm: {
        loginAccount: '',
        loginPassword: '',
        loginCode: ''
        //rememberMe: false
      },
      loginFormRules: {
        loginAccount: [
          { required: true, message: this.$t('loginUI.loginAccountInfo'), trigger: 'blur' },
          { min: 3, max: 10, message: this.$t('loginUI.loginInputInfo',{min: '3',max: '10'}), trigger: 'blur' }
        ],
        loginPassword:[
          { required: true, message: this.$t('loginUI.loginPasswordInfo'),trigger: 'blur'},
          { min:3, max:12, message: this.$t('loginUI.loginInputInfo',{min: '3',max: '12'}), trigger: "blur"}
        ],
        loginCode:[
          { required: true, message: this.$t('loginUI.loginVerifyCodeInfo'),trigger: 'blur'},
          { min:4, max: 6, message: this.$t('loginUI.loginInputInfo',{min: '4',max: '6'}), trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    getCaptchaImage(){
      this.captchaUrl = '/backendServer/getCaptcha?time='+new Date;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //alert('submit!');
          this.isLoading=true;
          this.postRequest("/backendServer/managerLogin",this.loginForm).then(resp=>{
                this.stopLoading();
                if(resp){
                  if(this.isLoading == true){
                    this.isLoading = false;
                  }
                  const respTokenHead = resp.obj.tokenHead+resp.obj.token;
                  window.sessionStorage.setItem('managerToken',respTokenHead);
                  this.$store.commit('setManagerTokenInfo',respTokenHead);//记录token
                  this.$store.commit('setManagerAccountId',resp.obj.accountId);
                  window.sessionStorage.setItem('managerID',resp.obj.accountId);
                  window.sessionStorage.setItem('username',resp.obj.username);
                  window.sessionStorage.setItem('loginRole','roleManager');
                  //console.log(resp.obj.accountId);
                  this.$router.replace("/managerWelcome");
                }
                //console.log(resp);
              }
          )
        } else {
          this.stopLoading();
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    stopLoading(){
      if(this.isLoading == true){
        setTimeout(()=>{
          this.isLoading=false;
        },1200);
      }
    }
  }
}
</script>

<style scoped>
  .demo-ruleForm{
    background-clip: padding-box;
    margin: 0px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
  }
  #managerLoginDiv{
    width: 50%;
    margin: 180px auto 0px auto;
    box-shadow: 0 4px 6px rgba(0, 0, 0, .12), 0 2px 6px rgba(0, 0, 0, .04);
  }
  .loginTitle {
    margin: 35px auto 0px auto;
    text-align: center;
    color: #505458;
  }
  .el-form-item__content{
    display: flex;
    align-items: center;
  }
</style>