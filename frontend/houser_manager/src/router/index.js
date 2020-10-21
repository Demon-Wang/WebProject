import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import Welcome from '../components/Welcome.vue'
import Users from '../components/user/Users.vue'
import Admin from '../components/user/Admin.vue'
import RentHouse from '../components/house/RentHouse.vue'
import AddRent from '../components/house/AddRent.vue'
import EditRent from '../components/house/EditRent.vue'
import SaleHouse from '../components/house/SaleHouse.vue'
import AddSale from '../components/house/AddSale.vue'
import EditSale from '../components/house/EditSale.vue'
import MsgRent from '../components/message/MsgRent.vue'
import MsgSale from '../components/message/MsgSale.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  {
    path: '/home',
    component: Home,
    redirect:'/welcome',
    children:[
      {path: '/welcome' , component: Welcome},
      {path: '/users' , component: Users},
      {path:'/admin', component: Admin},
      {path:'/rentHouse', component: RentHouse},
      {path:'/house/addRent', component: AddRent},
      {path:'/house/editRent', component: EditRent},
      {path:'/saleHouse', component: SaleHouse},
      {path:'/house/addSale', component: AddSale},
      {path:'/house/editSale', component: EditSale},
      {path:'/msgRent', component: MsgRent},
      {path:'/msgSale', component: MsgSale}
    ]
  }
]
const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  next()
})

export default router