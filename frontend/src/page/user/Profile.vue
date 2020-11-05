<template>
  <div style="margin-top:6rem;" class="container">
    <div class="mx-5 card border-bottom-0">
      <div class="row" style="margin: 0;">
        <div class="col-12 col-md-2 px-0 py-3" v-if="!profileInfo.profile_image">
          <img
            class="rounded-circle"
            style="width:5rem;height:5rem;"
            src="../../assets/img/defualt_image.png"
            width="70"
            height="70"
          />
        </div>
        <div class="col-12 col-md-2 px-0 py-3" v-else>
          <img
            class="rounded-circle"
            style="width:5rem;height:5rem;"
            :src="profileInfo.profile_image"
            width="70"
            height="70"
          />
        </div>
        <div class="col-12 col-md-6 py-3 ml-5 text-left my-auto" style="position:relative;">
          <div class="d-flex">
            <p
              style="font-family: 'Do Hyeon', sans-serif;"
              class="m-0 p-0"
            >{{profileInfo.nickname}}님</p>
          </div>
          <small
            style="position:absolute;top:37px;font-family: 'Do Hyeon', sans-serif;"
            class="m-0 p-0"
          >{{profileInfo.email}}</small>
          <br />
          <small class="mb-4">{{profileInfo.intro}}</small>
        </div>
        <div class="col-12 col-md-3 py-3 my-auto ml-auto">
          <div class="actions2" v-if="isAdmin">
            <b-button class="btn2 mb-0" @click="goAdmin">관리자</b-button>
          </div>
          <div class="actions2">
            <b-button class="btn2 mt-2 mb-0" v-b-modal.modal-1>수정</b-button>
          </div>
          <div class="actions2">
            <b-button class="btn2 mt-2 mb-0" v-b-modal.modal-2>탈퇴</b-button>
          </div>
        </div>
      </div>
      <div class="row mt-3 border-top border-bottom" style="margin:0;">
        <div
          class="selecting col-12 col-md-4 py-4 border-bottom border-right"
          style="cursor:pointer;"
          @click="page=0"
        >
          <p
            style="font-family: 'Do Hyeon', sans-serif;"
            class="m-0"
          >{{plusUnleaderLists.length + readyLists.length + plusLeaderLists.length + comLists.length + ingLists.length}}</p>
          <small style="font-family: 'IBMPlexSansKR-Text';color:orange;">STUDY</small>
        </div>
        <div
          class="selecting col-12 col-md-4 py-4 border-bottom border-right"
          style="cursor:pointer;"
          @click="page=1"
        >
          <p style="font-family: 'Do Hyeon', sans-serif;" class="m-0">{{total_mileage}}</p>
          <small style="font-family: 'IBMPlexSansKR-Text';color:orange;">MILEAGE</small>
        </div>
        <div class="selecting col-12 col-md-4 py-4" style="cursor:pointer;" @click="page=2">
          <p style="font-family: 'Do Hyeon', sans-serif;" class="m-0">{{total_score}} / 5</p>
          <small style="font-family: 'IBMPlexSansKR-Text';color:orange;">SCORE</small>
        </div>
      </div>

      <div class="border-bottom" v-if="page===0">
        <div class="text-left py-3 col-12">
          <small
            v-if="ingLists.length>0||plusUnleaderLists.length>0"
            class="text-success font-weight-bold text-left pl-3"
          >진행중 스터디</small>
          <div
            v-for="list in ingLists"
            :key="list.id"
            class="card m-2 px-2 p-2"
            @click="goStudyMain(list.pid)"
            style="cursor:pointer;"
          >
            <div class="d-flex inline">
              <small class="text-left mr-2">{{ list.empId.study.title }}</small>
              <b-badge class="ml-auto my-auto" variant="success">진행중</b-badge>
            </div>
          </div>

          <div
            v-for="list in plusUnleaderLists"
            :key="list.id"
            class="card m-2 px-2 p-2"
            @click="goStudyMain(list.pid)"
            style="cursor:pointer;"
          >
            <div class="d-flex inline">
              <small class="text-left mr-2">{{ list.empId.study.title }}</small>
              <b-badge class="ml-auto my-auto" variant="success">진행중</b-badge>
            </div>
          </div>

          <div
            class="my-2 mt-4"
            v-if="readyLists.length>0||comLists.length>0||plusLeaderLists.length>0"
          >
            <small class="text-warning font-weight-bold text-left pl-3 pb-2">모집중 스터디</small>
          </div>
          <div
            v-for="list in readyLists"
            :key="list.id"
            class="card m-2 px-2 p-2"
            @click="goStudyMain(list.pid)"
            style="cursor:pointer;"
          >
            <div class="d-flex inline">
              <small class="text-left mr-2">{{ list.empId.study.title }}</small>
              <b-badge class="ml-auto my-auto" variant="secondary">승인대기중</b-badge>
            </div>
          </div>

          <div
            v-for="list in comLists"
            :key="list.id"
            class="card m-2 px-2 p-2"
            @click="goPostMain(list.pid)"
            style="cursor:pointer;"
          >
            <div class="d-flex inline">
              <small class="text-left mr-2">{{ list.empId.study.title }}</small>
              <b-badge class="ml-auto my-auto" variant="warning">모집중</b-badge>
            </div>
          </div>

          <div
            v-for="list in plusLeaderLists"
            :key="list.id"
            class="card m-2 px-2 p-2"
            @click="goPostMain(list.pid)"
            style="cursor:pointer;"
          >
            <div class="d-flex inline">
              <small class="text-left mr-2">{{ list.empId.study.title }}</small>
              <b-badge class="ml-auto my-auto" variant="info">추가모집중</b-badge>
            </div>
          </div>

          <div class="my-2 mt-4" v-if="endLists.length>0">
            <small class="text-secondary font-weight-bold text-left pl-3 pb-2">완료된 스터디</small>
          </div>
          <div
            v-for="list in endLists"
            :key="list.id"
            class="card m-2 px-2 p-2"
            @click="goPostMain(list.pid)"
            style="cursor:pointer;"
          >
            <div class="d-flex inline">
              <small class="text-left mr-2">{{ list.empId.study.title }}</small>
            </div>
          </div>
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
            <div class="col-12 col-md-4 py-3 border-bottom border-right" style="cursor:pointer;">
              <div class="d-flex">
                <b-icon icon="calendar-3"></b-icon>
                <p class="mb-0 ml-2" style="font-family: 'Do Hyeon', sans-serif;">일지</p>
              </div>
              <div class="text-right pt-2 pr-2">
                <span class="milepoint">{{ mileageData.diarypoint }}</span>
                <small>점</small>
              </div>
            </div>

            <div class="col-12 col-md-4 py-3 border-bottom border-right" style="cursor:pointer;">
              <div class="d-flex">
                <b-icon icon="award"></b-icon>
                <p class="mb-0 ml-2" style="font-family: 'Do Hyeon', sans-serif;">평가</p>
              </div>
              <div class="text-right pt-2 pr-2">
                <span class="milepoint">{{ mileageData.evalpoint }}</span>
                <small>점</small>
              </div>
            </div>

            <div class="col-12 col-md-4 py-3" style="cursor:pointer;">
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

          <div class="d-flex m-3 mb-5 row rounded-lg border">
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

    <b-modal @ok="userUpdate" id="modal-1" title="회원정보 수정" ok-only>
      <div class="mb-2">
        <small style="font-family: 'Do Hyeon', sans-serif;">닉네임</small>
        <b-card class="baseInput p-2" no-body id="input-email">{{updateData.nickname}}</b-card>
      </div>
      <div class="mb-2">
        <small style="font-family: 'Do Hyeon', sans-serif;">이메일</small>
        <b-card class="baseInput p-2" no-body id="input-email">{{updateData.email}}</b-card>
      </div>
      <div class="mb-2">
        <small style="font-family: 'Do Hyeon', sans-serif;">비밀번호</small>
        <b-form-input type="password" v-model="updateData.password"></b-form-input>
      </div>
      <div class="mb-2">
        <small style="font-family: 'Do Hyeon', sans-serif;">자기소개</small>
        <b-form-textarea v-model="updateData.intro" rows="3" max-rows="6"></b-form-textarea>
      </div>
      <div class="mb-2">
        <small style="font-family: 'Do Hyeon', sans-serif;">프로필 사진을 선택해주세요</small>
        <div v-if="!updateData.profile_image">
          <b-form-file id="file-large" @change="onChangeImages"></b-form-file>
        </div>
        <div class="text-center" v-else>
          <img
            :src="updateData.profile_image"
            style="position:relative;width: 10rem;height:10rem;border-radius: 50%;"
          />
          <div>
            <b-button
              style="position:absolute;bottom:14%;left:43%;z-index:10;"
              v-if="updateData.profile_image"
              class="m-2 float-right"
              variant="danger"
              size="sm"
              @click="removeImage"
            >
              <b-icon icon="trash"></b-icon>
              <small>삭제</small>
            </b-button>
          </div>
        </div>
      </div>
    </b-modal>

    <b-modal @ok="userDelete" id="modal-2" title="회원 탈퇴" ok-only>
      <div class="mb-2">
        <small style="font-family: 'Do Hyeon', sans-serif;">이메일</small>
        <b-form-input v-model="deleteData.email" placeholder="가입 시 작성한 이메일을 입력해주세요"></b-form-input>
      </div>
      <div class="mb-2">
        <small style="font-family: 'Do Hyeon', sans-serif;">비밀번호</small>
        <b-form-input
          type="password"
          v-model="deleteData.password"
          placeholder="가입 시 작성한 비밀번호를 입력해주세요"
        ></b-form-input>
      </div>
    </b-modal>
  </div>
</template>

<script>
import "../../assets/css/profile.scss";
import axios from "axios";
import constants from "../../lib/constants";
import "sweetalert2/dist/sweetalert2.min.css";
import swal from "sweetalert";
const SERVER_URL = constants.ServerUrl;

export default {
  name: "profile",
  data: () => {
    return {
      profileInfo: {},
      adminList:[
        "ektha3869@naver.com",
        "sksk9877@nate.com",
        "3864617@naver.com",
        "kimsoo5133@gmail.com",
        "jaerim@kakao.com"
      ],
      isAdmin:false,
      updateData: {
        email: "",
        nickname: "",
        password: "",
        intro: "",
        profile_image: "",
        uid: "",
      },
      deleteData: {
        email: "",
        password: "",
      },
      page: 0,
      showEvalist: 3,

      // 리스트 7 종류
      readyLists: [],
      comLists: [],
      ingLists: [],
      endLists: [],
      plusLists: [],
      plusLeaderLists: [],
      plusUnleaderLists: [],

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
  created() {
    this.addprofileInfo();
  },
  methods: {
    getAdmin(){
      if (this.adminList.indexOf(this.profileInfo.email) != -1){
        this.isAdmin = true
      }
    },
    goAdmin(){
      this.$router.push({name:constants.URL_TYPE.ADMIN})
    },
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
      axios
        .get(SERVER_URL + "/mileage/user", {
          params: { uid: this.profileInfo.uid },
        })
        .then((res) => {
          this.mileageData = res.data.object;
        })
        .catch((err) => console.log(err));
    },
    changeDatedata(time) {
      return time.substring(5, 10) + " " + time.substring(11, 19);
    },
    addprofileInfo() {
      const token = this.$cookies.get("Auth-Token");
      axios
        .get(SERVER_URL + "/account/profile", { params: { Token: token } })
        .then((res) => {
          this.profileInfo = res.data.object;
          this.updateData.email = res.data.object.email;
          this.updateData.nickname = res.data.object.nickname;
          this.updateData.password = res.data.object.password;
          this.updateData.uid = res.data.object.uid;
          this.updateData.intro = res.data.object.intro;
          this.updateData.profile_image = res.data.object.profile_image;
          this.addReadyList();
          this.addStudyList();
          this.mileageList();
          this.evaList();
          this.getAdmin()
        })
        .catch((err) => {
          this.$router.push({
            name: constants.URL_TYPE.ERROR.ERRORPAGE,
            params: { code: err.response.data },
          });
        });
    },
    addStudyList() {
      axios
        .post(SERVER_URL + "/account/studylist", this.profileInfo)
        .then((res) => {
          this.ingLists = res.data.object.filter(
            (item) => item.empId.study.tmp == 0
          );
          this.comLists = res.data.object.filter(
            (item) => item.empId.study.tmp == 1
          );
          this.endLists = res.data.object.filter(
            (item) => item.empId.study.tmp == 2
          );
          this.plusLists = res.data.object.filter(
            (item) => item.empId.study.tmp == 3
          );
          this.plusLeaderLists = this.plusLists.filter(
            (item) => item.isleader == 1
          );
          this.plusUnleaderLists = this.plusLists.filter(
            (item) => item.isleader == 0
          );
        })
        .catch((err) => {
          console.log(err);
        });
    },
    addReadyList() {
      axios
        .post(SERVER_URL + "/account/readylist", this.profileInfo)
        .then((res) => {
          this.readyLists = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    onChangeImages(e) {
      const selectedImage = e.target.files[0];
      this.createBase64Image(selectedImage);
    },
    createBase64Image(fileObject) {
      this.updateData.profile_image = new Image();
      const reader = new FileReader();
      reader.onload = (e) => {
        this.updateData.profile_image = e.target.result;
      };
      reader.readAsDataURL(fileObject);
    },
    removeImage: function (e) {
      this.updateData.profile_image = "";
    },
    userUpdate() {
      axios
        .post(SERVER_URL + "/account/update", this.updateData)
        .then((res) => {
          this.$cookies.remove("Auth-Token");
          const token = res.data.object;
          this.$cookies.set("Auth-Token", token);
          swal("회원정보가 수정되었습니다.", { buttons: false, timer: 1200 });
          this.addprofileInfo();
        })
        .catch((err) => {
          this.$swal(
            "",
            err.response.data.errors[0].field + "를 확인해 주세요",
            "error"
          );
        });
    },
    userDelete() {
      swal({ text: "탈퇴하시겠습니까?", dangerMode: true, buttons: true }).then(
        (willDelete) => {
          if (willDelete) {
            axios
              .post(SERVER_URL + "/account/delete", this.deleteData)
              .then((res) => {
                this.$cookies.remove("Auth-Token");
                this.$swal("탈퇴 완료", "", "success");
                this.$router.push("/")
                this.$router.go();
                
              })
              .catch((err) => {
                this.$swal("", "입력정보를 확인해주세요.", "error");
                console.log(err);
              });
          }
        }
      );
    },
    goRank() {
      this.$router.push({
        name: constants.URL_TYPE.RANK.RANKING,
      });
    },
    goPostMain(post_id) {
      this.$router.push({
        name: constants.URL_TYPE.POST.POSTDETAIL,
        params: { post_id: post_id },
      });
    },
    goStudyMain(post_id) {
      this.$router.push({
        name: constants.URL_TYPE.STUDY.STUDYMAIN,
        params: { post_id: post_id },
      });
    },
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
/* .selecting:active{color:orange;
  background-color:rgba(255,165,0,0.3);
}
.selecting:visited{color:orange;
  background-color:rgba(255,165,0,0.3);
} */
.ranking:hover {
  color: orange;
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
.milelog {
  margin: 5px;
  padding-left: 5vw;
  font-size: 25px;
  font-weight: bolder;
  text-align: left;
}
.milepoint {
  font-family: "Do Hyeon", sans-serif;
  font-size: 2.5rem;
  color: orange;
}
</style>