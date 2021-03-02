import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

/*export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})*/

const store = new Vuex.Store({
      state: {
        userTokenInfo: '',
          userAccountId: -1,
        managerTokenInfo: '',
          managerAccountId: -1
      },
      mutations: {//this.$store.commit('setTokenInfo',payload)
        /*setTokenInfo(state,payload){
          state.tokenInfo = payload;
        }*/
        setUserTokenInfo: (state,payload) => {
          state.userTokenInfo = payload;
        },
          setAccountId: (state,payload) => {
          state.userAccountId = payload;
        },
        setManagerTokenInfo: (state,payload) => {
          state.managerTokenInfo = payload;
        },
          setManagerAccountId: (state,payload) => {
              state.managerAccountId = payload;
          },
      },
      actions: {//this.$store.dispatch('setTokenInfo',payload)

      },
      getters: {//this.$store.getters.getTokenInfo
        getUserTokenInfo: state => {
          return state.userTokenInfo;
        },
        getManagerTokenInfo: state => {
          return state.managerTokenInfo;
        },
          getManagerAccountId: state=>{
            return state.managerAccountId;
          },
          getUserAccountId: state=>{
              return state.userAccountId;
          }
      }
    }
)
export default store
