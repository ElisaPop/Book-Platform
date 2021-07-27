import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userData: [],
    userRole: 0
  },
  getters:{
    userData: state =>{
      return state.userData
    },
    userRole: state =>{
      return state.userRole
    }
  },
  mutations: {
    setUserData(state,data){
      state.userData = data
    },
    setUserRole(state,role){
      state.userRole = role
    }
  }
})
