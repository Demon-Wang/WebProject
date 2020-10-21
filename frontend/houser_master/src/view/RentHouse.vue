<template>
  <div>
    <cheader></cheader>
    <div style="height: 80px;background: #f5f5f6;width: 100%">
      <div style="width: 60%;margin:  0 auto;">
        <el-row>
          <el-col :span="5" style="padding-left:0px;">
            <h2 style="color: #00ae66;">天大房产</h2>
          </el-col>
        </el-row>
      </div>
    </div>
    <div style="width: 60%;margin: 0 auto;padding: 20px">
      <div style="padding-top: 10px">
          <el-row class="csearch" type="flex" justify="left">
            <el-col :span="14">
              <el-input
                placeholder="请输入内容"
                v-model="queryInfo.query"
                class="input"
                style="border-radius: 0px"
                clearable
                @clear="getHouseList"
                @keyup.enter.native="searchHouse"
              ></el-input>
            </el-col>
            <el-button type="success" class="cbtn-bg" @click="searchHouse">开始找房</el-button>
          </el-row>
      </div>
      <el-divider></el-divider>
      <el-row class="crow">
        <label>类型:  </label>
        <el-checkbox label="整租" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="合租" true-label="1" false-label="0"></el-checkbox>
      </el-row>
      <el-row class="crow">
        <label>租金/月:  </label>
        <el-checkbox label="2000以下" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="2000-5000" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="5000-10000" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="1万以上" true-label="1" false-label="0"></el-checkbox>
      </el-row>
      <el-row class="crow">
        <label>房型:  </label>
        <el-checkbox label="一室" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="二室" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="三室" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="三室及以上" true-label="1" false-label="0"></el-checkbox>
      </el-row>
      <el-row class="crow">
        <label>面积:  </label>
        <el-checkbox label="50㎡以下" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="50㎡-70㎡" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="70㎡-90㎡" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="90㎡-110㎡" true-label="1" false-label="0"></el-checkbox>
        <el-checkbox label="110㎡以上" true-label="1" false-label="0"></el-checkbox>
      </el-row>
    </div>
    <div style="width: 60%;margin: 0 auto;padding: 20px">
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="1">默认排序</el-menu-item>
        <el-menu-item index="3">最新发布</el-menu-item>
        <el-menu-item index="4">房屋租金</el-menu-item>
        <el-menu-item index="5">房屋面积</el-menu-item>
        <el-menu-item index="6">最短租期</el-menu-item>
      </el-menu>
      <el-row>
        <h2>共找到{{total}}套可租房源</h2>
      </el-row>
      <el-divider></el-divider>
      <el-row style="width:100%">
        <el-col style="width:85%;float:left">
          <el-row
            :gutter="20"
            style="height: 200px;padding-top:20px;cursor:pointer;border-bottom: 1px #DCDFE6 solid"
            v-for="item in houselist"
            :key="item.id"
          @click.native="info(item.id)">
            <el-col :span="4" style="height: 100%;">
              <el-row style="height: 90%;">
                <el-image :src="item.imagelist[0]" style="height: 100%;"></el-image>
              </el-row>
            </el-col>
            <el-col :span="16">
              <el-row>
                <h2>{{item.name}}</h2>
              </el-row>
              <el-row class="crow">
                <span>{{item.framework}}|</span>
                <span>{{item.area}}㎡|</span>
                <template>
                  <span v-if="item.elevator===1">有电梯|</span>
                  <span v-else >无电梯|</span>
                </template>
                <template>
                  <span v-if="item.share===1">合租|</span>
                  <span v-else >整租|</span>
                </template>
                <span>{{item.leaseTerm}}个月起租|</span>
                <template>
                  <el-tag size="mini" v-if="item.state===2">已完成</el-tag>
                  <el-tag size="mini" v-else type="danger">未完成</el-tag>
                </template>
              </el-row>
            </el-col>
            <el-col :span="4" style="height: 100%;">
              <span style="color:red;font-weight: bold;font-size: 28px">{{item.price}}元/月</span>
              <br />
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
    <cfooter></cfooter>
  </div>
</template>

<script>
import cheader from "@/components/cheader";
import cfooter from "@/components/cfooter";
export default {
  components: {
    cheader: cheader,
    cfooter: cfooter
  },
  data() {
    return {
      searchContent: "",
      activeIndex: "1",
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 10
      },
      houselist: [],
      total: 0
    };
  },
  created() {
    this.getHouseList();
  },
  methods: {
    handleSelect(key, keyPath) {
    },
    async getHouseList() {
      const { data:res } = await this.$http.get("house/getRentHouses", {
        params:{}
      });
      if(res.code !== 1000) return this.$message('获取房屋出租信息失败')
      this.houselist = res.data
      for(let a in this.houselist){
          this.houselist[a].imagelist = this.houselist[a].images.split(';')
      }
      this.total = res.data.length
    },
    async searchHouse() {
      name=this.queryInfo.query
      const { data:res } = await this.$http.get(`house/getHousesByNameLike/${name}`);
      if(res.code !== 1000) return this.$message('查询房屋出租失败')
      this.houselist = res.data
      for(let a in this.houselist){
          this.houselist[a].imagelist = this.houselist[a].images.split(';')
      }
      this.total = res.data.length
    },
    info(id) {
        this.$router.push({path:"house/RentInfo",query:{ id: `${id}` }});
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getHouseList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage;
      this.getHouseList();
    },
  }
};
</script>

<style scoped>
.cheader {
  width: 100%;
  height: 220px;
  background: #f5f5f6;
}
span {
  color: #000;
}
.csearch {
  width: 100%;
}
.cbtn-bg {
  background: #00ae66;
  border: none;
  border-radius: 0px;
}
.crow {
  line-height: 30px;
}
</style>
