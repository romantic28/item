<template>
  <div>
    <el-card class="margin-top-xs">
      <div slot="header" class="clearfix">
        <el-button type="primary" size="small" @click="TableSearch"
          >查询</el-button
        >
        <el-button type="success" size="small" @click="Reset"
          >清空条件</el-button
        >
      </div>
      <el-form :inline="true" :model="Search" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input v-model="Search.Name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="Search.Email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input
            v-model="Search.PhoneNumber"
            placeholder="请输入手机号码"
          ></el-input>
        </el-form-item>
        <!-- <el-form-item label="角色" prop="RoleType">
          <SigleSelect
            v-model="search.RoleType"
            url="/Select/RoleType"
            columnName="Name"
            columnValue="Code"
            columnLabel="Label"
          ></SigleSelect>
        </el-form-item> -->
      </el-form>
    </el-card>
    <el-card class="margin-top-xs">
      <div slot="header" class="clearfix">
        <el-button type="primary" size="small" @click="ShowEditModal()"
          >新增</el-button
        >
        <el-button type="danger" size="small" @click="BatchShowDeleteModal()"
          >批量删除</el-button
        >
        <el-button type="warning" size="small">导出</el-button>
      </div>
      <el-table :data="DataList" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column
          prop="UserName"
          label="用户名"
          width="180"
          align="center"
        >
        </el-table-column>
        <el-table-column prop="Name" label="姓名" width="120" align="center">
        </el-table-column>
        <el-table-column prop="Password" label="密码" align="center">
        </el-table-column>
        <el-table-column prop="Email" label="邮箱" width="200" align="center">
        </el-table-column>
        <el-table-column prop="PhoneNumber" label="联系方式" align="center">
        </el-table-column>
        <el-table-column prop="RoleTypeFormat" label="角色" align="center">
        </el-table-column>
        <el-table-column prop="Birth" label="出生日期" align="center">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="250" align="center">
          <template slot-scope="scope">
            <el-button
              @click="ShowEditModal(scope.row.Id)"
              size="small"
              type="primary"
              >编辑</el-button
            >
            <el-button
              size="small"
              type="danger"
              @click="ShowDeleteModal(scope.row.Id)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="margin-top-lg">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :total="pagination.total"
          :current-page="pagination.page"
          :page-sizes="pagination.pageSizes"
          :page-size="pagination.limit"
        >
        </el-pagination>
      </div>
    </el-card>
    <el-dialog title="新增用户" :visible.sync="editShow" width="30%">
      <el-form
        ref="editModalForm"
        :model="FormData"
        label-width="80px"
        size="mini"
        :rules="rules"
      >
        <el-form-item label="账号" prop="UserName">
          <el-input v-model="FormData.UserName" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="Password">
          <el-input
            v-model="FormData.Password"
            clearable
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="Email">
          <el-input v-model="FormData.Email" clearable></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="Name">
          <el-input v-model="FormData.Name" clearable></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="PhoneNumber">
          <el-input v-model="FormData.PhoneNumber" clearable></el-input>
        </el-form-item>
        <el-form-item label="出生年月" prop="Birth">
          <el-col :span="11">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="FormData.Birth"
              style="width: 100%"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="角色" prop="RoleType">
          <el-radio-group v-model="FormData.RoleType" clearable>
            <el-radio
              v-for="(item, index) in roleOptions"
              :key="index"
              :label="item.Code"
            >
              {{ item.Name }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editShow = false">取 消</el-button>
        <el-button type="primary" @click="CreateOrEditForm()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      DataList: [],
      FormData: {},
      SelectRow: [],
      rules: {
        UserName: [
          { required: true, message: "该项为必填项", trigger: "blur" },
        ],
        Password: [
          { required: true, message: "该项为必填项", trigger: "blur" },
        ],
        Email: [
          { required: true, message: "该项为必填项", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              var reg = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
              if (!value || !reg.test(value)) {
                callback(new Error("请输入正确的邮箱"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
        Name: [{ required: true, message: "该项为必填项", trigger: "blur" }],

        PhoneNumber: [
          { required: true, message: "该项为必填项", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              var reg = /^1[34578]\d{9}$/;
              if (!value || !reg.test(value)) {
                callback(new Error("请输入正确的手机号"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
        Birth: [{ required: true, message: "该项为必填项", trigger: "blur" }],
        RoleType: [
          { required: true, message: "该项为必填项", trigger: "blur" },
        ],
      },
      pagination: {
        limit: 10,
        page: 1,
        total: 0,
        pageSizes: [10, 20, 50, 100, 200, 500, 1000],
      },
      roleOptions: [],
      editShow: false,
      Search: {},
    };
  },
  created() {
    this.ListApi();
  },
  methods: {
    //弹出新增或者编辑框
    async ShowEditModal(Id) {
      this.GetRoleTypeApi();
      let { Data } = await this.$Post("/user/Get", { Id: Id });
      if (Data.Id) {
        Data.RoleType = Data.RoleType.toString();
      }
      this.FormData = Data;
      console.log(Id);
      this.editShow = true;
    },
    async GetRoleTypeApi() {
      let {
        Data: { Items },
      } = await this.$Post("/Select/RoleType");
      this.roleOptions = Items;
    },
    /**
     * 点击保存的时候会触发
     */
    async CreateOrEditForm() {
      this.$refs.editModalForm.validate(async (valid) => {
        if (valid) {
          let { Success } = await this.$Post(
            "/user/CreateOrEdit",
            this.FormData
          );
          if (Success) {
            this.editShow = false;
            this.ListApi();
          }
        }
      });
    },
    async ListApi() {
      let {
        Data: { Items, TotalCount },
      } = await this.$Post("/user/List", {
        ...this.Search,
        Page: this.pagination.page,
        Limit: this.pagination.limit,
      });
      this.DataList = Items;
      this.pagination.total = TotalCount;
    },
    async handleSizeChange(e) {
      this.pagination.limit = e;
      this.ListApi();
    },
    async handleCurrentChange(e) {
      this.pagination.page = e;
      this.ListApi();
    },
    async TableSearch() {
      this.ListApi();
    },
    async Reset() {
      this.Search = {};
      this.ListApi();
    },
    async ShowDeleteModal(Id) {
      let comfirm = await this.$comm.ConfirmMessageBox({
        title: "提示",
        content: "你确定要删除该数据吗?",
      });
      if(comfirm==false){
        return;
      }
      let {Success,Msg} = await this.$Post("/user/Delete",{ Id: Id });
      if(Success){
        this.ListApi();
      }
    },
    async BatchShowDeleteModal(){
      if(this.SelectRow.length==0){
        this.$message.warning("请选择你要操作的数据!!!");
        return;
      }
      let comfirm = await this.$comm.ConfirmMessageBox({
        title: "提示",
        content: "你确定要删除这些数据吗?",
      });
      if(comfirm==false){
        return;
      }
      let Ids = this.SelectRow.map(x=>x.Id);
      let {Success,Msg} = await this.$Post("/user/BatchDelete",{ Ids: Ids });
      if(Success){
        this.ListApi();
      }
    },
    async handleSelectionChange(rows){
      this.SelectRow=rows
    }
  },
};
</script>

<style></style>
