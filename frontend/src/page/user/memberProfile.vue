<template>
  <div style="margin-top:6rem;" class="container">
    <div class="mx-5 card border-bottom-0">
      <div class="d-flex p-4">
        <div>
          <div v-if="!profileInfo.profile_image">
            <img
              class="rounded-circle"
              style="width:5rem;height:5rem;"
              src="../../assets/img/defualt_image.png"
              width="70"
              height="70"
            />
          </div>
          <div v-else>
            <img
              class="rounded-circle"
              style="width:5rem;height:5rem;"
              :src="profileInfo.profile_image"
              width="70"
              height="70"
            />
          </div>
        </div>
        <div style="position:relative;" class="text-left ml-3 my-auto">
          <div class="d-flex">
            <p
              style="font-family: 'Do Hyeon', sans-serif;"
              class="m-0 p-0"
            >{{profileInfo.nickname}}님</p>
          </div>
          <small
            style="position:absolute;top:22px;font-family: 'Do Hyeon', sans-serif;"
            class="m-0 p-0"
          >{{profileInfo.email}}</small>
          <br />
          <small>{{profileInfo.intro}}</small>
        </div>
      </div>
      <div class="d-flex row-cols-2 border-top border-bottom">
        <div class="selecting col py-4 border-right" style="cursor:pointer;" @click="page=1">
          <p style="font-family: 'Do Hyeon', sans-serif;" class="m-0">{{total_mileage}}</p>
          <small style="font-family: 'IBMPlexSansKR-Text';color:orange;">MILEAGE</small>
        </div>
        <div class="selecting col py-4" style="cursor:pointer;" @click="page=2">
          <p style="font-family: 'Do Hyeon', sans-serif;" class="m-0">{{total_score}} / 5</p>
          <small style="font-family: 'IBMPlexSansKR-Text';color:orange;">SCORE</small>
        </div>
      </div>
      <div class="border-bottom" v-if="page===1">
        <div class="text-left py-3 col-12">
          <div class="d-flex">
            <div>
              <small class="text-dark font-weight-bold text-left pl-3">마일리지 로그</small>
            </div>
            <div class="ml-auto mr-4" style="color:orange;cursor:pointer;">
              <small @click="goRank" class="ranking">
                <i class="ranking fas fa-crown"></i> 랭킹
              </small>
            </div>
          </div>
          <div class="m-3 row rounded-lg border">
            <div class="col-12 col-md-4 py-3 border-bottom border-right">
              <div class="d-flex">
                <b-icon icon="calendar-3"></b-icon>
                <p class="mb-0 ml-2" style="font-family: 'Do Hyeon', sans-serif;">일지</p>
              </div>
              <div class="text-right pt-2 pr-2">
                <span class="milepoint">{{ mileageData.diarypoint }}</span>
                <small>점</small>
              </div>
            </div>

            <div class="col-12 col-md-4 py-3 border-bottom border-right" >
              <div class="d-flex">
                <b-icon icon="award"></b-icon>
                <p class="mb-0 ml-2" style="font-family: 'Do Hyeon', sans-serif;">평가</p>
              </div>
              <div class="text-right pt-2 pr-2">
                <span class="milepoint">{{ mileageData.evalpoint }}</span>
                <small>점</small>
              </div>
            </div>

            <div class="col-12 col-md-4 py-3">
              <div class="d-flex">
                <b-icon icon="book-half"></b-icon>
                <p class="mb-0 ml-2" style="font-family: 'Do Hyeon', sans-serif;">스터디 완료</p>
              </div>
              <div class="text-right pt-2 pr-2">
                <span class="milepoint">{{ mileageData.endpoint * 200 }}</span>
                <small>점</small>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="border-bottom" v-if="page===2">
        <div class="text-left py-3 col-12">
          <small class="font-weight-bold text-left pl-3">평점</small>

          <div class="d-flex m-3 mb-3 row rounded-lg border">
            <div class="col-12 col-md-4 py-3 border-bottom border-right" style="cursor:pointer;">
              <div class="text-center">
                <p class="pt-3" style="font-family: 'Do Hyeon', sans-serif;">성실도</p>
              </div>
              <div class="w-100 pb-3 text-center">
                <b-form-rating
                  v-model="profileInfo.score1"
                  readonly
                  no-border
                  inline
                  color="#feb74d"
                  size="sm"
                ></b-form-rating>
                <br />
                <small>{{profileInfo.score1}} / 5</small>
              </div>
            </div>

            <div class="col-12 col-md-4 py-3 border-bottom border-right" style="cursor:pointer;">
              <div class="text-center">
                <p class="pt-3" style="font-family: 'Do Hyeon', sans-serif;">참여도</p>
              </div>
              <div class="w-100 pb-3 text-center">
                <b-form-rating
                  v-model="profileInfo.score2"
                  readonly
                  no-border
                  inline
                  color="#feb74d"
                  size="sm"
                ></b-form-rating>
                <br />
                <small>{{profileInfo.score2}} / 5</small>
              </div>
            </div>

            <div class="col-12 col-md-4 py-3" style="cursor:pointer;">
              <div class="text-center">
                <p class="pt-3" style="font-family: 'Do Hyeon', sans-serif;">인싸력</p>
              </div>
              <div class="w-100 pb-3 text-center">
                <b-form-rating
                  v-model="profileInfo.score3"
                  readonly
                  no-border
                  inline
                  color="#feb74d"
                  size="sm"
                ></b-form-rating>
                <br />
                <small>{{profileInfo.score3}} / 5</small>
              </div>
            </div>
          </div>
          <small v-if="evalistdata.length>0" class="font-weight-bold text-left text-info pl-3">한줄평</small>

          <div
            v-for="(list1, n) in evalistdata"
            :key="list1.id"
            class="card m-3 px-2 p-2"
          >
            <div v-if="n < showEvalist" class="d-flex inline">
              <b-badge class="my-auto" variant="secondary">{{list1.study.category}}</b-badge>
              <small class="text-left ml-2">{{ list1.sentence }}</small>
            </div>
          </div>
          <button
            v-if="evalistdata.length>3"
            @click="showEvalist += 3"
            variant="link"
            class="small"
          >...더보기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/profile.scss";
import axios from "axios";
import constants from "../../lib/constants";

const SERVER_URL = constants.ServerUrl;

export default {
  name: "profile",
  data: () => {
    return {
      profileInfo: {},
      page: 1,
      showEvalist: 3,

      evalistdata: {},
      mileageData: {},
    };
  },
  computed: {
    total_score() {
      return (
        (this.profileInfo.score1 +
          this.profileInfo.score2 +
          this.profileInfo.score3) /
        3
      ).toFixed(1);
    },
    total_mileage() {
      if ("+this.mileageData.total".length > 3) {
        return this.mileageData.total / 1000 + "K";
      } else {
        return this.mileageData.total;
      }
    },
  },
  methods: {
    evaList() {
      const targetid = this.profileInfo.uid;
      axios
        .post(SERVER_URL + "/eva/userlist", { target_uid: targetid })
        .then((res) => {
          this.evalistdata = res.data.object;
        })
        .catch((err) => console.log(err));
    },
    mileageList() {
      const targetid = this.profileInfo.uid;
      axios
        .get(SERVER_URL + "/mileage/user", { params: { uid: targetid } })
        .then((res) => {
          this.mileageData = res.data.object;
        })
        .catch((err) => console.log(err));
    },
    addprofileInfo() {
      axios
        .get(SERVER_URL + "/account/memprofile", {
          params: { uid: this.$route.params.user_id },
        })
        .then((res) => {
          this.profileInfo = res.data.object;
          this.evaList();
          this.mileageList();
        })
        .catch((err) => {
          this.$router.push({
            name: constants.URL_TYPE.ERROR.ERRORPAGE,
            params: { code: err.response.data },
          });
        });
    },
    goRank() {
      this.$router.push({
        name: constants.URL_TYPE.RANK.RANKING,
      });
    },
  },
  created() {
    this.addprofileInfo();
  },
};
</script>

<style scoped>
.selecting {
  background-color: rgba(255, 165, 0, 0.1);
}
.selecting:hover {
  background-color: rgba(255, 165, 0, 0.4);
}
.clickbtn,
.tmplist {
  cursor: pointer;
}
.form-control {
  background-color: rgba(0, 0, 0, 0);
}
.b-rating .b-rating-star,
.b-rating .b-rating-value {
  padding: 0 !important;
}
.milepoint {
  font-family: "Do Hyeon", sans-serif;
  font-size: 2.5rem;
  color: orange;
}
</style>