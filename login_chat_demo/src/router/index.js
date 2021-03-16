import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import HomeVue from "../views/HomeVue.vue"

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
    component: ()=>import('../views/UserLogin.vue')
  },
  {
    path: '/managerLoginVue',
    name: 'ManagerLogin',
    component: ()=>import('../views/ManagerLogin.vue')
  },
  {
    path: '/welcome',
    name: 'Welcome',
    component: ()=>import('../views/Welcome.vue')
  },
  {
    path: '/userWelcome',
    name: 'WelcomeUser',
    component: ()=>import('../views/UserWelcome.vue')
  },
  {
    path: '/managerWelcome',
    name: 'WelcomeManager',
    component: ()=>import('../views/ManagerWelcome.vue')
  },
  {
    path: '/userInfoVue',
    name: 'UserInfoVue',
    component: ()=>import('../views/UserInfoVue.vue')
  },
  {
    path: '/managerInfoVue',
    name: 'ManagerInfoVue',
    component: ()=>import('../views/ManagerInfoVue.vue')
  },
  {
    path: '/chatVue',
    name: 'ChatVue',
    component: ()=>import('../views/ChatVue.vue')
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
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
