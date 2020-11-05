<template>
  <div id="container" class="fixed-top" v-if="isHeader">
  </div>
</template>   

<script>
import constants from "../../lib/constants";
import LoginModal from "../../modal/LoginModal";
import axios from "axios";

const SERVER_URL = constants.ServerUrl;

export default {
  name: "Header",
  components: {
    constants,
  },
  props: ["isHeader"],
  computed: {
  },
  created() {
  },
  methods: {
  }
};
</script>

<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
#brandname {
  font-family: "Do Hyeon", sans-serif;
  font-size: x-large;
  color: orange;
}
.dropdown-menu {
  font-size: 100px !important;
}

.profileImg {
  width: 70px;
  height: 70px;
}

.iconcss {
  font-size: 30px;
  color: orange;
}

.listitem {
  font-family: "Do Hyeon", sans-serif;
  background-color: orange;
  color: white;
  /* border-radius: 15px; */
}

.listitem:hover {
  font-family: "Do Hyeon", sans-serif;
  color: white;
  background-color: rgb(255, 145, 0);
}
.nameandemail {
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>