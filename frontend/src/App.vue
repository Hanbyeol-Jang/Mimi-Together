<template>
  <div id="app">
    <Header :isHeader="isHeader" />

    <router-view />
    <!-- <Footer :isFooter="isFooter" /> -->
  
    <button class="" onclick="history.back()" >
      <b-icon class="backButton rounded-circle p-1 font-weight-bold" style="border:2px solid orange;" icon="reply-fill" flip-h></b-icon>
    </button>

    <button @click="moveTop">
      <b-icon class="topButton rounded-circle p-1 font-weight-bold" style="border:2px solid orange;" icon="shift-fill"></b-icon>
    </button>
  </div>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>  
<script>
import "./assets/css/style.scss";
import Header from "./components/common/Header.vue";
// import Footer from "./components/common/Footer.vue";
import constants from "./lib/constants";

export default {
  name: "App",
  components: {
    Header,
    // Footer
  },
  created() {
    let url = this.$route.name;

    this.checkUrl(url);
  },
  watch: {
    $route(to) {
      this.checkUrl(to.name);
    },
  },
  methods: {
    checkUrl(url) {
      // let array = [constants.URL_TYPE.USER.LOGIN, constants.URL_TYPE.USER.JOIN];
      let array = [
        constants.URL_TYPE.MAIN,
        constants.URL_TYPE.USER.LOGIN,
        constants.URL_TYPE.USER.JOIN,
        constants.URL_TYPE.USER.UPDATE,
        constants.URL_TYPE.USER.DELETE,
        constants.URL_TYPE.USER.FINDPASSWORD,
        constants.URL_TYPE.ERROR.PAGENOTFOUND,
      ];

      let isHeader = true;
      let isFooter = true;
      array.map((path) => {
        if (url === path) {
          isHeader = false;
          isFooter = false;
        }
      });
      this.isHeader = isHeader;
      this.isFooter = isFooter;
    },
    moveTop() {
      var location = document.querySelector("#brandname").offsetTop;
      window.scrollTo({ top: location, behavior: "smooth" });
    },
  },
  data: function () {
    return {
      isHeader: true,
      isFooter: true,
      constants,
    };
  },
};
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
}

.backButton {
  position: fixed;
  bottom: 50px;
  left: 50px;
  font-size: 30px;
  color:orange;
}
.backButton:hover,
.topButton:hover{
  color:white;
  background-color:orange;
}
.topButton {
  position: fixed;
  bottom: 50px;
  right: 50px;
  font-size: 30px;
  color:orange;
}

</style>
