<template>
  <div>
    <el-container>
      <el-header>
        <div class="header-box">
          <div class="name">系统名称</div>
          <div class="menu-wrapper">
            <el-avatar size="medium" >
              <img
                src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
              />
            </el-avatar>
            <el-dropdown class="menu-list">
              <span class="el-dropdown-link">
                管理员<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item><div @click="LoginOut()">退出</div></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu class="aside-box" :router="true">
            <el-menu-item index="/Admin/Home">
              <i class="el-icon-menu"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-menu-item index="/Admin/UserList">
              <i class="el-icon-menu"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
            <el-menu-item index="3" disabled>
              <i class="el-icon-document"></i>
              <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">导航四</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>
          <el-main>
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item v-for="(item,index) in breadList" :key="index" :to="{ path: item.path }">
                {{ item.meta && item.meta.title }}</el-breadcrumb-item>
              
            </el-breadcrumb>
            <router-view></router-view>
          </el-main>
          <el-footer class="footer-box">
            <div>
              <span>版权归系统所有</span>
            </div>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      breadList: [],
    }
  },
  watch: {
    $route() {
            this.getBreadcrumb();
        },
  },
  created() { 
    this.getBreadcrumb();
  },
  methods: {
    async LoginOut() {
            console.log("点击退出")
            await this.$store.dispatch('Logout')
            this.$router.push(`/Login`);
        },
    isHome(route) {
            return route.path === "/Admin";
        },
    getBreadcrumb() {
            let matched = this.$route.matched;
            if (matched[matched.length-1].path=="/Admin/Home") {
                matched = [{ path: "/Admin", meta: { title: "控制台" } }];
            }
            this.breadList = matched;

        }
  }
};
</script>

<style scoped>
.el-header {
  padding: 0px;
}
.header-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: inherit;
  background-color: #1a91d1;
  padding: 0px 20px;
}
.header-box .name {
  color: #fff;
}
.header-box .menu-wrapper {
  display: flex;
  align-items: center;
}
.header-box .menu-wrapper .menu-list {
  margin-left: 10px;
}
.header-box .menu-wrapper .menu-list .el-dropdown-link {
  color: #fff;
}
.aside-box {
  height: calc(100vh - 60px);
}
.footer-box {
  color: #000;
  background-color: rgb(241, 245, 245);
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
