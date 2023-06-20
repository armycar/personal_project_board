import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Detail from '../views/Detail.vue'
import WrtieView from '../views/WriteView.vue'
import LoginView from '../views/LoginView.vue'
import JoinView from '../views/JoinView.vue'
import MyPageView from '../views/MyPageView.vue'
import SearchPwd from '../views/SearchPwd.vue'
import UpdateMemberInfo from '../views/UpdateMemberInfo.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/api/article/detail/:seq',
    name : 'Detail',
    component : Detail
  },
  {
    path: '/api/article/write/',
    name: 'Write',
    component: WrtieView,
    props: (route) => ({
      seq:route.query.seq,
      cateSeq: route.query.cateSeq
    })
  },
  {
    path: '/api/member/login',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/api/member/join',
    name: 'joinView',
    component: JoinView
  },
  {
    path: '/mypage',
    name : 'MyPageView',
    component : MyPageView,
    props: true
  },
  {
    path: '/api/member/search/pwd',
    name: 'SearchPwd',
    component: SearchPwd
  },
  {
    path: '/api/member/change/memberInfo',
    name: 'UpdateMemberInfo',
    component: UpdateMemberInfo
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
