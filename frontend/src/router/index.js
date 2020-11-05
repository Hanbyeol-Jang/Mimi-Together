import Vue from "vue";
import Router from "vue-router";
import constants from "../lib/constants";

// 유저
import Profile from "../page/user/Profile.vue";
import Signup from "../page/user/Signup.vue";
import MemberProfile from '../page/user/memberProfile'
import Login from '../page/user/Login'

// 포스트
import List from "../page/post/List.vue";
// 404 오류
import PageNotFound from "../page/error/PageNotFound.vue";
import ErrorPage from "../page/error/ErrorPage.vue";
import Duplicate from "../page/error/Duplicate.vue";

Vue.use(Router);

export default new Router({
  routes: [
    // 유저
    {
      path: "/user/profile",
      name: constants.URL_TYPE.USER.PROFILE,
      component: Profile,
    },
    {
      path: "/user/signup",
      name: constants.URL_TYPE.USER.SIGNUP,
      component: Signup,
    },
    {
      path: "/",
      name: constants.URL_TYPE.USER.LOGIN,
      component: Login,
    },
    // 포스트
    {
      path: "/auction",
      name: constants.URL_TYPE.POST.MAIN,
      component: List,
    },

    {
      path: "/user/:user_id",
      name: constants.URL_TYPE.USER.MEMBERPROFILE,
      component: MemberProfile,
    },

    //page not found
    {
      path: "*",
      name: constants.URL_TYPE.ERROR.PAGENOTFOUND,
      component: PageNotFound,
    },
    {
      path: "/error",
      name: constants.URL_TYPE.ERROR.ERRORPAGE,
      component: ErrorPage,
      props: true,
    },
    {
      path: "/duplicate",
      name: constants.URL_TYPE.ERROR.DUPLICATE,
      component: Duplicate,
    },
  ],
});
