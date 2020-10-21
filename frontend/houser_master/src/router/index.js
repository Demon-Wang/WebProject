import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../view/Home.vue'
import Index from '../view/Index.vue'
import Login from '../view/Login.vue'
import RentHouse from '../view/RentHouse.vue'
import SaleHouse from '../view/SaleHouse.vue'
import HouseRentInfo from '../view/house/RentInfo.vue'
import HouseSaleInfo from '../view/house/SaleInfo.vue'
import Msg from '../view/Msg.vue'
import AddHouse from '../view/AddHouse.vue'
import MyHouse from '../view/MyHouse.vue'
import MyMsg from '../view/MyMsg.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/home' },
  { path: '/home', component: Home },
  { path: '/login', component: Login },
  { path: '/index', component: Index },
  { path: '/rentHouse', component: RentHouse},
  { path: '/saleHouse', component: SaleHouse},
  { path: '/house/RentInfo', component: HouseRentInfo},
  { path: '/house/SaleInfo', component: HouseSaleInfo},
  { path: '/msg', component: Msg},
  { path: '/addHouse', component: AddHouse},
  { path: '/myHouse', component: MyHouse},
  { path: '/myMsg', component: MyMsg}
]

const router = new VueRouter({
  routes
})

export default router
