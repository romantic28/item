import Vue from 'vue'
import Vuex from 'vuex'
import { Post } from '@/api/http.js'
import { getToken, setToken, removeToken } from '@/utils/cache.js'
Vue.use(Vuex)

const getDefailtState = () => {
    return {
        Token: getToken(),
        UserInfo: null,//用户信息
        RoleType: undefined,//用户角色
        HasUserInfo: false,//是否根据token获取了用户信息
    }
}


const store = new Vuex.Store({
    state: getDefailtState(),
    //对state进行赋值操作
    mutations: {
        SET_TOKEN(state,token) {
            state.Token = token
        },
        SET_USER_INFO: (state, UserInfo) => {
            state.UserInfo = UserInfo;
            state.HasUserInfo = UserInfo != null;

        },
        SET_ROLE_TYPE: (state, RoleType) => {
            state.RoleType = RoleType;

        }
    },
    actions: {
        //登录,userInfo是前端传进去的值
        async Login({ commit }, UserInfo){
            let res = await Post("/user/SignIn", UserInfo);

            if (res.Success) {
                commit('SET_TOKEN', res.Data);
                setToken(res.Data)
            }
            return res;
        },
         // 根据token获取用户的信息
         async GetInfo({ commit, state }) {
            const res = await Post('/user/GetByToken', {});
            console.log(res)
            commit("SET_USER_INFO", res.Data);
            commit("SET_ROLE_TYPE", res.Data.RoleTypeFormat);

        },
         //退出登录
         async Logout({ commit }, UserInfo) {

            removeToken();
            commit('SET_TOKEN', null);
            commit("SET_USER_INFO", null);
            commit("SET_ROLE_TYPE", null);
        },
    },
    getters: {
        Token: state => state.Token,
        UserInfo: state => state.UserInfo,
        RoleType: state => state.RoleType,
        HasUserInfo: state => state.HasUserInfo,
    }
})

export default store