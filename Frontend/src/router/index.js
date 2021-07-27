import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Books from '../views/Books.vue'
import Tags from '../views/Tags.vue'
import Users from '../views/Users.vue'
import LogIn from '../views/LogIn.vue'
import SignUp from '../views/SignUp.vue'
import Profile from '../views/Profile.vue'
import Ratings from '../views/Ratings.vue'
import ToRead from '../views/ToRead.vue'
import Written from '../views/Written.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/books',
    name: 'Books',
    component: Books
  },
  {
    path: '/books/:id',
    name: 'Book',
    component: () => import("../components/Book.vue")
  },
  {
    path: '/tags',
    name: 'Tags',
    component: Tags
  },
  {
    path: '/tags/:id',
    name: 'Tag',
    component: () => import("../components/Tag.vue")
  },
  {
    path: '/users',
    name: 'Users',
    component: Users
  },
  {
    path: '/users/:id',
    name: 'User',
    component: () => import("../components/User.vue")
  },
  {
    path: '/login',
    name: 'LogIn',
    component: LogIn
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/ratings',
    name: 'Ratings',
    component: Ratings
  },
  {
    path: '/toread',
    name: 'ToRead',
    component: ToRead
  },
  {
    path: '/written',
    name: 'Written',
    component: Written
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
