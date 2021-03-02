import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import HomeVue from "@/views/HomeVue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeVue
    //component: Home
  },
  {
    path: '/userLoginVue',
    name: 'UserLogin',
    component: ()=>import('../views/UserLogin')
  },
  {
    path: '/managerLoginVue',
    name: 'ManagerLogin',
    component: ()=>import('../views/ManagerLogin')
  },
  {
    path: '/welcome',
    name: 'Welcome',
    component: ()=>import('../views/Welcome')
  },
  {
    path: '/userWelcome',
    name: 'WelcomeUser',
    component: ()=>import('../views/UserWelcome')
  },
  {
    path: '/userManager',
    name: 'WelcomeManager',
    component: ()=>import('../views/ManagerWelcome')
  },
  {
    path: '/userInfoVue',
    name: 'UserInfoVue',
    component: ()=>import('../views/UserInfoVue')
  }
  /*{
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: function () {
      return import(/!* webpackChunkName: "about" *!/ '../views/About.vue')
    }
  }*/
]

const router = new VueRouter({
  mode:'history',
  routes
})

export default router
