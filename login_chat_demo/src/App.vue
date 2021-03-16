<template>
  <div id="app">
    <!--<div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>-->
    <div id="navDiv">
      <!-- 父组件传输收到的子组件name给导航组件   -->
      <NavigationComponent v-bind:routeViewName="appRouteView"></NavigationComponent>
    </div>
    <!-- 子组件传输自己的name给父组件-->
    <router-view v-on:fromRouterView="setAppRouteViewName"/>
  </div>
</template>

<style>
/* https://returnc.com/detail/3736 */
  @import "./themes/green_theme/index.css";
/*#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}*/
</style>
<script>
export default {
  created() {
  },
  data(){
    return{
      appRouteView: ''
    }
  },
  methods:{
    setAppRouteViewName(routeViewName){
      this.appRouteView = routeViewName;
    }
  },
  watch:{
    appRouteView(newAppRouteView){
      console.log('app appRouteView : '+newAppRouteView)
    }
  }
}
</script>