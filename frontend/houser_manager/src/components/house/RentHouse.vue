<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>房屋管理</el-breadcrumb-item>
      <el-breadcrumb-item>出租房屋列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getHouseList">
            <el-button slot="append" icon="el-icon-search" @click="getHouseName"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="goAddRentpage">添加房屋信息</el-button>
        </el-col>
      </el-row>
      <el-table :data="houselist" border stripe style="width: 100%">
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="name" label="房屋名称"></el-table-column>
        <!-- <el-table-column prop="phone" label="户型"></el-table-column> -->
        <el-table-column prop="area" label="建筑面积/平方米" width="120px"></el-table-column>
        <el-table-column prop="framework" label="户型" width="120px"></el-table-column>
        <el-table-column prop="price" label="价格(元)/月" width="100px"></el-table-column>
        <el-table-column label="合租/整租" width="100px">
          <template v-slot="scope">
            <span v-if="scope.row.share===1">合租</span>
            <span v-else >整租</span>
          </template>
        </el-table-column>
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
          <!-- 编辑出租信息 -->
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" @click="goEditRentpage(scope.row.id)" size="mini"></el-button>
            <!-- 删除用户 -->
            <el-button type="danger" icon="el-icon-delete" @click="deleteHouse(scope.row.id)" size="mini"></el-button>
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

    
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 10
      },
      houselist: [],
      total: 0
    }
  },
  created(){
    this.getHouseList();
  },
  methods: {
    async getHouseList() {
      const { data:res } = await this.$http.get("house/getRentHouses", {
        params:{}
      });
      if(res.code !== 1000) return this.$message('获取房屋出租信息失败')
      this.houselist = res.data
      this.total = res.data.length
    },
    async getHouseName() {
      name=this.queryInfo.query
      const { data:res } = await this.$http.get(`house/getHousesByNameLike/${name}`);
      if(res.code !== 1000) return this.$message('查询房屋出租失败')
      this.houselist = res.data
      this.total = res.data.length
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getHouseList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage;
      this.getHouseList();
    },
    goAddRentpage() {
      this.$router.push("house/AddRent");
    },
    goEditRentpage(id) {
      this.$router.push({path:"house/EditRent",query:{ id: `${id}` }});
    },
    deleteHouse(id) {
      this.$confirm("此操作将永久删除该租房信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const { data:res } = await this.$http.post(`house/delHouseByHouseId/${id}`);
          if (res.code !== 1000) {
            this.$message.error(res.msg);
          } else {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getHouseList();
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
    this.getHouseList();
  }
};
</script>

<style lang="less" scoped></style>
