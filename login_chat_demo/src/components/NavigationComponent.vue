<template>
  <div id="navMain">
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        text-color="#C8E6C9"
        background-color="#27DA93"
        active-text-color="#FFFFFF">
      <el-menu-item index="1"><a href="/">{{ $t('uiText.loginNav')}}</a></el-menu-item>
      <el-menu-item index="2"><a href="/welcome" target="_blank">{{ $t('uiText.homePage')}}</a></el-menu-item>
      <el-submenu index="3">
        <template slot="title">{{ this.$t('uiText.languageSelect')}}</template>
        <el-menu-item index="3-1">中文(简体)</el-menu-item>
        <el-menu-item index="3-2">English</el-menu-item>
        <el-menu-item index="3-3">日本語</el-menu-item>
      </el-submenu>
      <el-menu-item index="4" :disabled="disablePeronInfoNav">
        <a v-bind:href="personInfoHref">{{ $t('uiText.personInfoPage')}}</a>
      </el-menu-item>
      <el-menu-item index="5" :disabled="disableWelcomeNav">
        <a v-bind:href="welcomeHref">{{ $t('uiText.welcomeLogin')}}</a>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import {simpleChinese,japanese,unitedStatesAmerica} from '../i18n/language-constants'

export default {
  name: "NavigationComponent",
  created() {
    this.initNavStatus();
    this.handleNavRouteView();
  },
  props:{
    routeViewName:{
      type: String,
      default: ''
    }
  },
  data() {
    return {
      nowRouterViewName: '',
      activeIndex: '1',
      disablePeronInfoNav: true,
      disableWelcomeNav: true,
      personInfoHref: '/userInfoVue',
      welcomeHref: '/userWelcome'
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      let setLang;
      if(key === '3-1'){
        setLang = simpleChinese;
      }else if(key === '3-2'){
        setLang = unitedStatesAmerica;
      }else if(key === '3-3'){
          setLang = japanese;
      }
      console.log('handleSelect: lang = '+setLang)
      if(setLang){
        this.getRequest('/backendServer/changeLanguage',{'lang' : setLang}).then(resp=>{
          console.log('changeLanguage resp:'+resp)
          if(resp && resp.code == 200){
            //刷新页面
            //this.$router.push(this.getRefreshPath(this.nowRouterViewName));
            window.sessionStorage.setItem('lang',setLang);
            //localStorage.setItem('lang',langI18n);
            this.$i18n.locale = setLang;
            /*setTimeout(()=>{
              this.$router.go(0)
            },1500)*/
          }
        })
      }
      console.log(this.activeIndex);
      console.log(key, keyPath);
    },
    handleNavRouteView(){
      let routerViewName = this.nowRouterViewName;
      console.log(routerViewName)
      if(routerViewName === 'UserLogin' || routerViewName === 'ManagerLogin' || routerViewName === 'Home'){
        this.activeIndex = '1';
      }else if(routerViewName === 'Welcome'){
        this.activeIndex = '2';
      }else if(routerViewName === 'UserInfoVue' || routerViewName === 'ManagerInfoVue'){
        this.activeIndex = '4';
      }else if(routerViewName === 'WelcomeUser' || routerViewName === 'WelcomeManager'){
        this.activeIndex = '5';
      }
    },
    initNavStatus(){
      if(window.sessionStorage.getItem('loginRole') === 'roleManager'){
        this.personInfoHref = '/managerInfoVue';
        this.welcomeHref = '/managerWelcome';
      }
      if(window.sessionStorage.getItem('username')){
        this.disablePeronInfoNav = false;
        this.disableWelcomeNav = false;
      }
    },
    getRefreshPath(routerViewName){
      //根据vue名字获取访问路径
      let routeArray = this.$router.getRoutes()
      for(let routeIndex in routeArray){
        if(routeArray[routeIndex].name === routerViewName){
          return routeArray[routeIndex].path
        }
      }
    }
  },
  watch:{
    routeViewName(newValue){
      this.nowRouterViewName = newValue;
      this.handleNavRouteView();
      this.initNavStatus();
    }
  }
}
</script>

<style scoped>
  #navMain{
    background-color: #27DA93;
  }
  .el-menu{
    width: 80%;
    margin: 0px auto 50px auto;
  }
  a{
    text-decoration: none;
  }
  el-menu-item.disabled{
    color: #BDBDBD;
  }
</style>