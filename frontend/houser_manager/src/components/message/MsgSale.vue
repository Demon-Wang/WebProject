<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息管理</el-breadcrumb-item>
      <el-breadcrumb-item>求购信息列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getmsgList">
            <el-button slot="append" icon="el-icon-search" @click="getmsgText"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加求购信息</el-button>
        </el-col>
      </el-row>
      <el-table :data="msglist" border stripe style="width: 100%">
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="userId" label="用户id"></el-table-column>
        <el-table-column prop="text" label="内容"></el-table-column>
        <el-table-column prop="phone" label="联系方式"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="140px">
          <template v-slot="scope">
            {{scope.row.createTime | dateFormat}}
          </template>
        </el-table-column>
        <el-table-column label="是否完成">
          <template v-slot="scope">
            <el-tag size="mini" v-if="scope.row.state===2">已完成</el-tag>
            <el-tag size="mini" v-else type="danger">未完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <!-- 编辑信息 -->
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.id)" size="mini"></el-button>
            <!-- 删除信息 -->
            <el-button type="danger" icon="el-icon-delete" @click="deletemsg(scope.row.id)" size="mini"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <el-dialog title="添加信息" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form :model="addmsgForm" :rules="addmsgFormRules" ref="addmsgFormRef" label-width="90px" class="demo-ruleForm">
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="addmsgForm.userId"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="text">
          <el-input v-model="addmsgForm.text"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="addmsgForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addmsg">确 定</el-button>
      </span>
    </el-dialog>



    <el-dialog
      title="修改信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <el-form
        :model="editmsgForm"
        :rules="editmsgFormRules"
        ref="editmsgFormRef"
        label-width="70px"
        class="demo-ruleForm"
      >
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="editmsgForm.userId"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="text">
          <el-input v-model="editmsgForm.text"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="editmsgForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editmsg">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
export default {
  data() {
    // 自定义手机号校验规则
    var checkMobile = (rule, value, cb) => {
      const regMobile = /^[1][3,4,5,7,8][0-9]{9}$/;
      if (regMobile.test(value)) {
        return cb();
      }
      cb(new Error("请输入合法的手机号"));
    };
    return {
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 10
      },
      msglist: [],
      total: 0,
      addDialogVisible: false,
      addmsgForm: {
        userId: "",
        phone: "",
        text: "",
        type: 2
      },
      addmsgFormRules: {
        text: [
          { required: true, message: "请输入信息内容", trigger: "blur" },
          {
            require: true,
            min: 10,
            message: "信息长度至少在 10 个字符以上",
            trigger: "blur"
          }
        ],
        userId: [
          { required: true, message: "请输入用户id", trigger: "blur" },
          {
            require: true,
            min: 1,
            max: 15,
            message: "用户id长度至少为 1 个字符",
            trigger: "blur"
          }
        ],
        phone: [
          { required: true, message: "请输入手机", trigger: "blur" },
          { validator: checkMobile, trigger: "blur" }
        ]
      },
      editmsgForm: {},
      editDialogVisible:false,
      editmsgFormRules: {
        msgname: [
          { required: true, message: "请输入信息名", trigger: "blur" },
          {
            require: true,
            min: 3,
            max: 10,
            message: "信息名长度在 3 到 10 个字符",
            trigger: "blur"
          }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            require: true,
            min: 6,
            max: 15,
            message: "密码长度在 6 到 15 个字符",
            trigger: "blur"
          }
        ],
        phone: [
          { required: true, message: "请输入手机", trigger: "blur" },
          { validator: checkMobile, trigger: "blur" }
        ],
        introduceSign:[],
        lockedFlag:[],
        isDeleted:[]
      },
    }
  },
  methods: {
    async getmsgList() {
      const { data:res } = await this.$http.get("demandMsg/getAskRentMsgs", {
        params:{}
      });
      if(res.code !== 1000) return this.$message('获取信息列表失败')
      this.msglist = res.data
      this.total = res.data.length
    },
    async getmsgText() {
      const { data:res } = await this.$http.get(`demandMsg/getMsgByTextLike/${this.queryInfo.query}`);
      if(res.code !== 1000) return this.$message('查询信息失败')
      this.msglist = res.data
      this.total = res.data.length
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getmsgList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage;
      this.getmsgList();
    },
    addDialogClosed() {
      this.$refs.addmsgFormRef.resetFields();
    },
    addmsg() {
      this.$refs.addmsgFormRef.validate(async valid => {
        if (!valid) return;
        const { data:res } = await this.$http.post("demandMsg/addMsg", this.addmsgForm);
        if (res.code === 1000) {
          this.$message.success("添加信息成功！");
        }
        else{
          this.$message.error("添加信息失败！");
        }
        this.addDialogVisible = false;
        this.getmsgList();
      });
    },
    async showEditDialog(id) {
      const { data:res } = await this.$http.get(`demandMsg/getMsgById/${id}`);
      if (res.code !== 1000) {
        this.$message.error("查询信息失败！");
      }
      this.editmsgForm = res.data;
      this.editDialogVisible = true;
    },
    editDialogClosed() {
      this.$refs.editmsgFormRef.resetFields();
    },
    editmsg() {
      this.$refs.editmsgFormRef.validate(async valid => {
        if (!valid) return;
        const { data:res } = await this.$http.post("demandMsg/updateMsg", this.editmsgForm);
        if (res.code === 1000) {
          this.$message.success("修改信息信息成功！");
        }
        else{
          this.$message.error("修改失败！");
        }
        this.editDialogVisible = false;
        this.getmsgList();
      });
    },
    deletemsg(id) {
      this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const { data:res } = await this.$http.post(`demandMsg/delMsgById/${id}`);
          if (res.code !== 1000) {
            this.$message.error(res.msg);
          } else {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getmsgList();
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
  },
  created() {
    this.getmsgList();
  }
};
</script>

<style lang="less" scoped></style>
