<template>
  <div class="login-page">
    <div class="box-center">
      <div class="left">
        <img :src="require('@/assets/login1.png')" />
      </div>
      <div class="right">
        <h2>你好欢迎登录</h2>
        <template>
          <el-form
            ref="loginForm"
            :model="FormData"
            label-width="70px"
            label-position="left"
            style="max-width: 600px"
            :rules="rules"
            class="login-form"
          >
            <el-form-item label="账号" prop="UserName">
              <el-input v-model="FormData.UserName" placeholder="请输入账号" />
            </el-form-item>
            <el-form-item label="密码" prop="Password">
              <el-input
                type="password"
                v-model="FormData.Password"
                placeholder="请输入密码"
              />
            </el-form-item>
            <el-form-item label="角色" prop="RoleType">
              <el-radio-group v-model="FormData.RoleType">
                <el-radio
                  v-for="(item, index) in roleOptions"
                  :key="index"
                  :label="item.Code"
                  >{{ item.Name }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
            <el-form-item label="验证码" prop="Code">
              <div class="Code-wapper">
                <el-input
                  v-model="FormData.Code"
                  style="width: 240px"
                  placeholder="Please input"
                />
                <ValidCode
                  ref="ValidCode"
                  style="margin-left: 10px; margin-top: 10px"
                ></ValidCode>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button class="login-btn" type="primary" @click="loginBtn()"
                >登录</el-button
              >
            </el-form-item>
            <div style="display: flex; justify-content: space-between">
              <div>
                <span
                  >如果没有账号可以<span
                    style="padding-left: 10px; color: red"
                    class="pointer"
                    >立即注册</span
                  ></span
                >
              </div>
              <div>
                <span style="color: #3366ff" class="pointer">忘记密码</span>
              </div>
            </div>
          </el-form>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import Code from "@/components/Code/canvas.vue";
import store from "@/store/index";
export default {
  components: {
    ValidCode: Code,
  },
  data() {
    return {
      FormData: {
        UserName: "",
        Password: "",
        RoleType: "",
        Code: "",
      },
      roleOptions: [],
      rules: {
        UserName: [{ required: true, message: "请输入账号", trigger: "blur" }],
        Password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        RoleType: [{ required: true, message: "请选择角色", trigger: "blur" }],
        Code: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              let identifyCode = this.$refs.ValidCode.getCode();

              if (value != identifyCode) {
                callback(new Error("请输入正确的验证码"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.GetRoleTypeApi();
  },
  methods: {
    //得到角色集合
    async GetRoleTypeApi() {
      let {
        Data: { Items },
      } = await this.$Post("/Select/RoleType");
      this.roleOptions = Items;
    },
    //点击登录按钮执行方法
    async loginBtn() {
      this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          console.log(store);
          let res = await store.dispatch("Login", this.FormData);
          if (res.Success) {
            this.$message.success("登录成功!");
            this.$router.push({
              path: "/Admin"
            })
          } else {
            this.$refs.ValidCode.refreshCode();
          }
        } else {
          this.$message.error("登录验证不通过");
          this.$refs.ValidCode.refreshCode();
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.login-page {
  display: flex;
  background-color: #f98c54;
  justify-content: center;
  height: 100vh;
  align-items: center;
}
.login-page .box-center {
  margin-top: 10vh;
  padding: 20px;
  border-radius: 15px;
  background-color: #fff;
  width: 70%;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
}
.login-page .login-form{
  margin-top: 30px;
}
.login-page .box-center .left {
  width: 600px;
}

.login-page .box-center .left img {
  width: 600px;
  border-radius: 15px;
}
.login-page .box-center .right {
  flex: 1;
  padding-left: 20px;
}
.login-page .box-center .Code-wapper {
  display: flex;
  align-items: center;
}
</style>
