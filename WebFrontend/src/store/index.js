import Vue from 'vue';
import Vuex from 'vuex';
//容器
Vue.use(Vuex);
//如果使用code作为登录校验，每次刷新都会重新为0，导致再次返回登录界面
export default new Vuex.Store({
  state: {
    code: 0
  },
  mutations: {
    setCode(state, code) {
      state.code = code;
    }
  }
});
