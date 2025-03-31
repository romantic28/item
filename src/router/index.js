import Vue from 'vue'
import VueRouter from 'vue-router'
import AdminLayout from '@/views/Admin/layout/index.vue'
import FrontLayout from '@/views/Front/layout/index.vue'
import { getToken }from '@/utils/cache'
import store from "@/store/index"
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style


Vue.use(VueRouter)

const routes = [
  {
    path: '/Login',
    name: 'login',
    component : () => import('@/views/Login.vue')
  },
]
const FrontLayoutRoutes = [
  {
    path: '/',
    component: FrontLayout,
    meta: {
      isAdmin: false,
      title:'前台'
    },
    redirect: "/Front/Home",
    children: [
      {
        path: '/Front/Home',
        meta: {
          isAdmin: false,
          title:'前台首页'
        },
        component: () => import('@/views/Front/Home.vue')
      },
      
    ]
  }
]

const AdminLayoutRoutes = [
  {
    path: '/Admin',
    component: AdminLayout,
    meta: {
      isAdmin: true,
      title:'控制台'
    },
    redirect: "/Admin/Home",
    children: [
      {
        path: '/Admin/Home',
        meta: {
          isAdmin: true,
          title:'首页'
        },
        component: () => import('@/views/Admin/Home.vue')
      },
      {
        path: '/Admin/UserList',
        meta: {
          isAdmin: true,
          title:'用户列表'
        },
        component: () => import('@/views/Admin/UserList.vue')
      }
    ]
  }
]

const router = new VueRouter({
  routes:[...routes, ...AdminLayoutRoutes, ...FrontLayoutRoutes]
})

// 编写导航路由
router.beforeEach(async (to, from, next) => {

   NProgress.start()
  const hasToken = getToken()
 /*  if(hasToken){
    if(store.getters.HasUserInfo==false){
      await store.dispatch('GetInfo');
    }
    let role = store.RoleTypeFormat;
    if(to.meta&&to.meta.isAdmin){
      if(role=="管理员"){
        next()
      }
      else{
        next({path: "/Login"})
      }
    }
    else{
      if(role=="用户"){
        next()
      }
      else{
        next({path: "/Admin"})
      }
    }

  }
  else{
    //没登录但输入的路径是后台的路径则直接跳转回登录,否则正常走
    if(to.meta && to.meta.isAdmin){
      next({ path: "/Login"})
    }
    else{
      next()
    }

  } */
  if (hasToken) {

      if (store.getters.HasUserInfo == false) {
          await store.dispatch('GetInfo');
      }
      //如果去往的页面是后台
      if (to.meta && to.meta.isAdmin) {
          if (store.getters.RoleType == "管理员") {
              next()
          }
          else {
              next({ path: "/" })
          }
      }
      else {
          if (store.getters.RoleType == "管理员") {
              next({ path: "/Admin" })
          }
          else {
              next()
          }
      }

  }
  else {
      if (to.meta && to.meta.isAdmin) {
          next({ path: "/Login" })
      }
      else {
          next()
      }
  } 
  next()
  NProgress.done()

})
router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
export default router
