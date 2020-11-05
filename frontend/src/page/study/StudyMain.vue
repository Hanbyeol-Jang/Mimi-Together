<template>
  <div style="margin-top:6rem;" class="container">
    <div class="main-table">
      <div class="container p-2">
        <div class="card mb-4">
          <div class="card-body">
            <div class="pl-3">
              <div class="row">
                <div class="col-12 col-md-8">
                  <h3 class="pt-3 text-left font-weight-bold">{{ postData.title }}</h3>
                  <small class="float-left">{{ userData.nickname }}</small>
                  <br />
                  <small class="d-flex inline">{{postData.start_date}} ~ {{postData.end_date}}</small>
                </div>
                <div class="col-12 col-md-4 mt-3 float-right">
                  <div>
                    <div class="d-flex float-right">
                      <!-- 4. 집으로 -->
                      <div class="my-auto">
                        <b-button
                          size="sm"
                          class="border-0 d-flex"
                          variant="link"
                          @click="goPostMain(postData.pid)"
                        >
                          <b-icon style="cursor:pointer;" variant="dark" icon="house-door"></b-icon>
                          <small style="color:black;" class="ml-1">HOME</small>
                        </b-button>
                      </div>
                      <!-- 3. 게시판 -->
                      <div class="my-auto">
                        <b-button
                          @click="goBoard(postData.pid)"
                          size="sm"
                          class="border-0 d-flex"
                          variant="link"
                        >
                          <b-icon icon="file-text" variant="dark"></b-icon>
                          <small style="color:black;" class="ml-1">BOARD</small>
                        </b-button>
                      </div>
                      <!-- 1. SETTINGS -->
                      <b-dropdown
                        right
                        size="sm"
                        v-if="profileInfo.uid==postData.uid"
                        variant="link"
                        toggle-class="text-decoration-none"
                        no-caret
                      >
                        <template v-slot:button-content class>
                          <b-icon icon="gear" variant="dark"></b-icon>
                          <small class="ml-1" style="color:black;">SETTINGS</small>
                        </template>
                        <b-dropdown-item @click="postUpdate(postData.pid)">수정</b-dropdown-item>
                        <b-dropdown-item @click="postDelete">삭제</b-dropdown-item>
                        <b-dropdown-item v-b-modal.modal-5>멤버 탈퇴</b-dropdown-item>
                      </b-dropdown>
                    </div>
                    <div class="d-flex float-right">
                      <!-- 상호평가 -->
                      <div v-if="calculFri == '월요일' || finalCheck < 7">
                        <b-button
                          v-if="finalCheck > 7"
                          @click="checkEvalue"
                          variant="link"
                          v-b-modal.modal-multi-1
                          size="sm"
                          class="border-0"
                        >
                          <i class="fas fa-medal"></i>
                          <small class="ml-1" style="color: black;">EVALUATE</small>
                        </b-button>
                        <b-button
                          v-if="finalCheck <= 7"
                          @click="checkFinalEva"
                          size="sm"
                          class="border-0"
                          variant="link"
                          v-b-modal.modal-multi-1
                        >
                          <i class="fas fa-medal"></i>
                          <small class="ml-1" style="color: black;">EVALUATE</small>
                        </b-button>
                      </div>

                      <!-- 상호평가 모달 -->
                      <b-modal id="modal-multi-1" size="lg" title="상호 평가" hide-footer>
                        <b-list-group v-for="member in memberListData" :key="member.uid">
                          <b-list-group-item
                            v-if="profileInfo.uid != member.uid"
                            class="m-1 px-3 p-0"
                          >
                            <span style="line-height: 38px;">{{member.empId.user.nickname}}</span>
                            <b-button
                              v-if="alreadyEva.indexOf(member.uid)!=-1"
                              class="float-right"
                              @click="onceEva"
                              variant="link"
                            >
                              <i class="fas fa-star" style="color: orange; font-size: large;"></i>
                            </b-button>
                            <b-button
                              v-else
                              class="float-right"
                              @click="evalueData.target_uid = member.uid"
                              variant="link"
                              v-b-modal.modal-multi-2
                            >
                              <i class="far fa-star" style="color: orange; font-size: large;"></i>
                            </b-button>
                          </b-list-group-item>
                        </b-list-group>
                      </b-modal>

                      <b-modal
                        v-if="finalCheck > 7"
                        id="modal-multi-2"
                        @ok="setEvaluage"
                        title="상호 평가"
                        ok-only
                      >
                        <b-list-group>
                          <b-list-group-item class="p-1 pl-3 m-1">
                            <span style="line-height: 38px;">1. 일지를 성실하게 작성 하였는가</span>
                            <b-form-rating
                              class="float-right"
                              inline
                              v-model="evalueData.score1"
                              style="color: orange;"
                            ></b-form-rating>
                          </b-list-group-item>
                          <b-list-group-item class="p-1 pl-3 m-1">
                            <span style="line-height: 38px;">2. 적극적으로 참여 했는가</span>
                            <b-form-rating
                              class="float-right"
                              inline
                              v-model="evalueData.score2"
                              style="color: orange;"
                            ></b-form-rating>
                          </b-list-group-item>
                          <b-list-group-item class="p-1 pl-3 m-1">
                            <span style="line-height: 38px;">3. 팀원과의 화합을 노력하였는가</span>
                            <b-form-rating
                              class="float-right"
                              inline
                              v-model="evalueData.score3"
                              style="color: orange;"
                            ></b-form-rating>
                          </b-list-group-item>
                        </b-list-group>
                      </b-modal>

                      <b-modal
                        v-if="finalCheck <= 7"
                        id="modal-multi-2"
                        @ok="setFinEva"
                        title="마지막 상호 평가"
                        ok-only
                      >
                        <b-list-group>
                          <b-list-group-item class="p-1 pl-3 m-1">
                            <span style="line-height: 38px;">1. 일지를 성실하게 작성 하였는가</span>
                            <b-form-rating
                              class="float-right"
                              inline
                              v-model="evalueData.score1"
                              style="color: orange;"
                            ></b-form-rating>
                          </b-list-group-item>
                          <b-list-group-item class="p-1 pl-3 m-1">
                            <span style="line-height: 38px;">2. 적극적으로 참여 했는가</span>
                            <b-form-rating
                              class="float-right"
                              inline
                              v-model="evalueData.score2"
                              style="color: orange;"
                            ></b-form-rating>
                          </b-list-group-item>
                          <b-list-group-item class="p-1 pl-3 m-1">
                            <span style="line-height: 38px;">3. 팀원과의 화합을 노력하였는가</span>
                            <b-form-rating
                              class="float-right"
                              inline
                              v-model="evalueData.score3"
                              style="color: orange;"
                            ></b-form-rating>
                          </b-list-group-item>
                          <b-form-input
                            class="p-1 pl-3 m-1"
                            v-model="evalueData.sentence"
                            placeholder="한줄평을 작성해주세요"
                          ></b-form-input>
                        </b-list-group>
                      </b-modal>

                      <!-- 2. member -->
                      <div>
                        <b-button
                          v-b-modal.modal-multi-3
                          @click="reportCheck"
                          size="sm"
                          class="border-0"
                          variant="link"
                        >
                          <b-icon icon="people" variant="warning" class="mr-1"></b-icon>
                          <small style="color:black;">MEMBER</small>
                        </b-button>
                      </div>

                      <!-- 팀원 모달 -->
                      <b-modal id="modal-multi-3" title="팀원" hide-footer>
                        <ul v-for="per in memberListData" :key="per.uid" class="list-group mb-1">
                          <li class="list-group-item d-flex">
                            <!-- <li v-if="per.empId.user.uid==profileInfo.uid" class="list-group-item d-flex"> -->
                            <div class="d-flex my-auto">
                              <div
                                @click="goMemberProfile(per.uid)"
                                style="cursor:pointer;"
                              >{{ per.empId.user.nickname }}</div>
                              <div @click="goMemberProfile(per.uid)" style="cursor:pointer;">
                                <b-icon scale="0.8" icon="person"></b-icon>
                              </div>
                            </div>
                            <!-- <div v-if="profileInfo.uid!=postData.uid" class="ml-auto"> -->
                            <div class="ml-auto d-flex">
                              <div v-if="profileInfo.uid==per.empId.user.uid&&per.isleader==0">
                                <b-button
                                  v-if="per.isjoin==2"
                                  class="btn-sm mr-2"
                                  style="font-family:'Do Hyeon', sans-serif;"
                                  variant="outline-warning"
                                  @click="deleteMemberCancel(per.uid)"
                                >취소</b-button>
                                <b-button
                                  v-else
                                  class="btn-sm mr-2"
                                  style="font-family:'Do Hyeon', sans-serif;"
                                  variant="outline-warning"
                                  @click="deleteMember(per.uid)"
                                >탈퇴</b-button>
                              </div>
                              <div v-if="profileInfo.uid!=per.empId.user.uid||per.isleader==0">
                                <b-button
                                  v-if="profileInfo.uid==postData.uid"
                                  style="font-family:'Do Hyeon', sans-serif;"
                                  class="btn-sm mr-2"
                                  variant="outline-success"
                                  @click="delegation(per.uid)"
                                >위임</b-button>
                              </div>
                              <div v-if="profileInfo.uid!=per.empId.user.uid">
                                <b-button
                                  v-if="reportList.indexOf(per.empId.user.uid)!=-1"
                                  style="font-family:'Do Hyeon', sans-serif;"
                                  class="btn-sm"
                                  variant="outline-danger"
                                  @click="alertCheck"
                                >신고</b-button>
                                <b-button
                                  v-else
                                  v-b-modal.modal-multi-4
                                  style="font-family:'Do Hyeon', sans-serif;"
                                  class="btn-sm"
                                  variant="outline-danger"
                                  @click="reportdata.target=per.empId.user.uid"
                                >신고</b-button>
                              </div>
                              <b-badge
                                v-if="profileInfo.uid==per.empId.user.uid"
                                variant="warning"
                                size="sm"
                                class="ml-2 my-auto"
                                style="font-family:'Do Hyeon', sans-serif;"
                              >me</b-badge>
                            </div>
                          </li>
                        </ul>
                      </b-modal>
                      <b-modal id="modal-multi-4" title="신고 내용" hide-footer>
                        <div class="d-flex">
                          <textarea
                            class="border w-100 mr-2 rounded-lg p-2"
                            style="resize:none;"
                            v-model="reportdata.reason"
                          ></textarea>
                          <b-button
                            class="btn-sm my-auto float-right"
                            variant="outline-danger"
                            style="font-family:'Do Hyeon', sans-serif;"
                            @click="reportMember"
                          >신고</b-button>
                        </div>
                      </b-modal>
                    </div>
                  </div>

                  <b-modal id="modal-5" title="스터디원 탈퇴" hide-footer>
                    <ul v-for="per in DeleteMemberListData" :key="per.uid" class="list-group mb-1">
                      <li class="list-group-item d-flex">
                        <div class="d-flex">
                          <div
                            @click="goMemberProfile(per.uid)"
                            style="cursor:pointer;"
                          >{{ per.nickname }}</div>
                          <div @click="goMemberProfile(per.uid)" style="cursor:pointer;">
                            <b-icon scale="0.8" icon="person"></b-icon>
                          </div>
                        </div>
                        <div class="ml-auto">
                          <b-button
                            class="btn-sm float-right"
                            variant="outline-success"
                            style="font-family:'Do Hyeon', sans-serif;"
                            @click="DeleteApply(per.uid)"
                          >승인</b-button>
                          <b-button
                            class="btn-sm mr-2 float-right"
                            variant="outline-danger"
                            style="font-family:'Do Hyeon', sans-serif;"
                            @click="DeleteCancel(per.uid)"
                          >취소</b-button>
                        </div>
                      </li>
                    </ul>
                  </b-modal>
                </div>
              </div>
            </div>
            <hr />

            <!-- 다가올 스터디 일정 조회 -->
            <div class="d-flex pl-3">
              <div>
                <p
                  v-if="expectTodo.dodate!=null"
                  class="badge badge-pill badge-danger"
                >D - {{decimalDay}}</p>
                <p v-else class="badge badge-pill badge-danger">D-None</p>
              </div>
              <div class="ml-auto">
                <b-dropdown
                  right
                  size="sm"
                  v-if="profileInfo.uid==postData.uid"
                  variant="link"
                  toggle-class="text-decoration-none"
                  no-caret
                >
                  <template v-slot:button-content>
                    <b-icon class="mr-1" icon="gear" variant="dark"></b-icon>
                    <small style="color:black;">SETTINGS</small>
                  </template>
                  <b-dropdown-item v-b-modal.modal-3>생성</b-dropdown-item>
                  <b-dropdown-item v-b-modal.modal-4>수정</b-dropdown-item>
                  <b-dropdown-item @click="expectDelete">삭제</b-dropdown-item>
                </b-dropdown>
              </div>
            </div>
            <div v-if="expectTodo.dodate!=null" class="py-2 px-4 text-left">
              <p class="pb-3">
                <b-icon icon="geo-alt"></b-icon>
                {{expectTodo.doplace}}
              </p>
              <p class="pb-3">
                <b-icon icon="calendar-date"></b-icon>
                {{changeDatedata(expectTodo.dodate)}}
                <!-- {{expectTodo.dodate}} -->
              </p>
              <p>과제 : {{expectTodo.assignment}}</p>
            </div>
            <div class="py-2 px-4 text-left" v-else>
              <p>스터디 일정이 없습니다.</p>
              <p>다가 올 스터디 일정을 생성해주세요.</p>
            </div>
          </div>
        </div>

        <!-- 다가올 스터디 일정 모달 -->
        <div class="ml-auto text-right p-1" v-if="profileInfo.uid==postData.uid">
          <b-modal @ok="expectOk" id="modal-3" title="일정 추가" ok-only>
            <form ref="form">
              <b-form-group id="expectdoWrite">
                <div class="input-wrap mx-3">
                  <input
                    class="p-3 border-bottom"
                    v-model="expectData.doplace"
                    id="doplace"
                    placeholder="장소 입력"
                    type="text"
                  />
                </div>
                <div class="input-wrap mx-3">
                  <input
                    class="p-3 border-bottom"
                    v-model="expectData.dodate"
                    type="datetime-local"
                    id="dodate"
                    placeholder="날짜 입력"
                  />
                </div>
                <div class="input-wrap mx-3">
                  <input
                    class="p-3 border-bottom"
                    v-model="expectData.assignment"
                    type="text"
                    id="assignment"
                    placeholder="할일 입력"
                  />
                </div>
              </b-form-group>
            </form>
          </b-modal>
          <b-modal @ok="expectUpdate" id="modal-4" title="일정 업데이트" ok-only>
            <form ref="form">
              <b-form-group id="expectdoUpdate">
                <div class="input-wrap mx-3">
                  <input
                    class="p-3 border-bottom"
                    v-model="expectData.doplace"
                    id="doplace"
                    placeholder="장소 입력"
                    type="text"
                  />
                </div>
                <div class="input-wrap mx-3">
                  <input
                    class="p-3 border-bottom"
                    v-model="expectData.dodate"
                    type="datetime-local"
                    id="dodate"
                    placeholder="날짜 입력"
                  />
                </div>
                <div class="input-wrap mx-3">
                  <input
                    class="p-3 border-bottom"
                    v-model="expectData.assignment"
                    type="text"
                    id="assignment"
                    placeholder="할일 입력"
                  />
                </div>
              </b-form-group>
            </form>
          </b-modal>
        </div>

        <!-- 일지 card -->
        <div class="card" style="position:relative;width: 100%;">
          <b-button @click="goDailyCreate" class="diarybutton" variant="outline-dark" size="sm">
            <b-icon icon="pencil"></b-icon>일지 작성
          </b-button>
          <div v-if="profileInfo.uid==postData.uid" v-b-modal.modal-1 class="dailyselect">
            <b-icon class="mr-1" icon="calendar-check" variant="dark" shift-v="-1"></b-icon>
            <small style="color:black;font-family: 'Do Hyeon', sans-serif;">요일선택</small>
          </div>
          <div v-else class="d-flex dailyselect">
            <div class="d-flex" v-for="item in postData.days" :key="item.id">
              <small style="color:black;font-family: 'Do Hyeon', sans-serif;">{{item}}</small>
            </div>
          </div>

          <b-modal @ok="handleOk" id="modal-1" title="일지 작성가능한 요일을 설정하세요." ok-only>
            <form ref="form">
              <b-form-group>
                <b-form-checkbox-group v-model="selectedDay" v-for="day in week" :key="day">
                  <b-form-checkbox :value="day">{{day}}</b-form-checkbox>
                </b-form-checkbox-group>
              </b-form-group>
            </form>
          </b-modal>

          <FullCalendar :options="calendarOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/studymain.scss";
import axios from "axios";
import constants from "../../lib/constants";

const SERVER_URL = constants.ServerUrl;
import { Calendar } from "@fullcalendar/core";
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import "sweetalert2/dist/sweetalert2.min.css";
import swal from "sweetalert";

export default {
  name: "PostDetail",
  components: {
    FullCalendar,
  },
  data: () => {
    return {
      profileInfo: [],
      memberListData: [],
      postData: [],
      tagData: [],
      userData: [],

      expectTodo: [],
      expectData: {
        dodate: null,
        doplace: "",
        assignment: "",
        uid: "",
        pid: "",
        eid: 0,
      },
      decimalDay: "",

      selectedDay: [],
      week: [
        "일요일",
        "월요일",
        "화요일",
        "수요일",
        "목요일",
        "금요일",
        "토요일",
      ],
      days: [],

      DeleteMemberListData: [],
      // 일지
      allDailyLists: [],
      // 신고
      reportdata: {
        pid: "",
        target: "",
        reporter: "",
        reason: "",
      },
      ismodal: true,
      reportList: [],
      // 상호 평가
      evalueData: {
        pid: 0,
        writer_uid: 0,
        target_uid: 0,
        score1: 1,
        score2: 1,
        score3: 1,
        sentence: null,
        count: 0,
      },
      alreadyEva: [],
      // 달력
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, listPlugin],

        headerToolbar: {
          start: "prevYear,prev,next,nextYear", // will normally be on the left. if RTL, will be on the right
          center: "title",
          end: "dayGridMonth,dayGridWeek,listMonth",
        },

        initialView: "dayGridMonth",
        events: [],
      },
    };
  },
  computed: {
    calculFri() {
      var date = new Date();
      var d = this.week[date.getDay()];
      return d;
    },
    finalCheck() {
      var stday = this.postData.end_date;
      var today = new Date();
      var count = new Date(stday);
      var dday = Math.floor((count - today) / 1000 / 24 / 60 / 60);
      return dday;
    },
  },
  created() {
    window.scroll({ top: 0, left: 0, behavior: "smooth" });
    this.addprofileInfo();
    this.getDetail();
    this.DeleteMemberList();
  },
  mounted() {
    window.scroll({ top: 0, left: 0, behavior: "smooth" });
    this.getPostTime(0);
  },
  methods: {
    changeDatedata(time) {
      return time.substring(2, 10) + " " + time.substring(11, 16);
    },
    memberCheck() {
      var member = []
      for (let i = 0; i < this.memberListData.length; i++) {
        member.push(this.memberListData[i].uid)
      }
      if (member.indexOf(this.profileInfo.uid) == -1) {  
        swal("", "스터디 맴버가 아닙니다.");
        this.$router.push({ name: constants.URL_TYPE.POST.MAIN})
      }
    },
    postUpdate(post_id) {
      this.$router.push({
        name: constants.URL_TYPE.POST.POSTUPDATE,
        params: {
          post_id: post_id,
        },
      });
    },
    postDelete() {
      axios
        .post(SERVER_URL + "/study/delete", this.postData)
        .then((res) => {
          this.$router.push({
            name: constants.URL_TYPE.POST.MAIN,
          });
        })
        .catch((err) => console.log(err));
    },

    deleteMember(user_id) {
      const deleteData = {
        pid: this.$route.params.post_id,
        uid: user_id,
      };
      axios
        .post(SERVER_URL + "/study/detail/delete_request", deleteData)
        .then((res) => {
          this.memberList();
        })
        .catch((err) => console.log(err));
    },
    DeleteApply(memberid) {
      const indvData = {
        uid: memberid,
        pid: this.postData.pid,
        isLeader: 0,
        isJoin: 1,
      };
      axios
        .post(SERVER_URL + "/study/detail/delete_apply", indvData)
        .then((res) => {
          this.DeleteMemberList();
        })
        .catch((err) => console.log(err));
    },
    DeleteCancel(memberid) {
      const indvData = {
        uid: memberid,
        pid: this.postData.pid,
        isLeader: 0,
        isJoin: 1,
      };
      axios
        .post(SERVER_URL + "/study/detail/delete_companion", indvData)
        .then((res) => {
          this.DeleteMemberList();
        })
        .catch((err) => console.log(err));
    },
    deleteMemberCancel(user_id) {
      const deleteData = {
        pid: this.$route.params.post_id,
        uid: user_id,
      };
      axios
        .post(SERVER_URL + "/study/detail/delete_cancel", deleteData)
        .then((res) => {
          this.memberList();
        })
        .catch((err) => console.log(err));
    },
    DeleteMemberList() {
      axios
        .get(SERVER_URL + "/study/detail/deleteList", {
          params: {
            pid: this.$route.params.post_id,
          },
        })
        .then((res) => {
          this.DeleteMemberListData = res.data.object;
        })
        .catch((err) => console.log(err));
    },
    delegation(memberid) {
      const delegationData = {
        leader: this.postData.uid,
        member: memberid,
        pid: this.postData.pid,
      };
      axios
        .post(SERVER_URL + "/study/detail/delegation", delegationData)
        .then((res) => {
          this.memberList();
          this.getDetail();
        })
        .catch((err) => console.log(err));
    },
    addprofileInfo() {
      if (this.$cookies.isKey("Auth-Token")) {
        const token = this.$cookies.get("Auth-Token");
        axios
          .get(SERVER_URL + "/account/profile", {
            params: {
              Token: token,
            },
          })
          .then((res) => {
            this.profileInfo = res.data.object;
            this.memberList();
          })
          .catch((err) => {
            this.$router.push({
              name: constants.URL_TYPE.ERROR.ERRORPAGE,
              params: { code: err.response.data },
            });
          });
      } else {
        this.$router.push({
          name: constants.URL_TYPE.POST.MAIN,
        });
      }
    },
    getDetail() {
      const post_id = this.$route.params.post_id;
      axios
        .get(SERVER_URL + "/study/details", {
          params: {
            pid: post_id,
          },
        })
        .then((res) => {
          this.postData = res.data.object[0];
          this.tagData = res.data.object[2];
          this.userData = res.data.object[4];
          const tmpdays = [];
          if (this.postData.days != null) {
            for (var i = 0; i < this.postData.days.length; ) {
              tmpdays.push(this.postData.days.slice(i, i + 3));
              i += 3;
            }
            this.days = tmpdays;
          }
        })
        .catch((err) => {
          this.$router.go()
          this.$router.push({
            name: constants.URL_TYPE.POST.MAIN,
          });
        });
    },
    memberList() {
      axios
        .post(SERVER_URL + "/study/memberlist", {
          pid: this.$route.params.post_id,
        })
        .then((res) => {
          this.memberListData = res.data.object;
          this.memberCheck();
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    handleOk() {
      let dayString = [];
      for (var i = 0; i < this.selectedDay.length; i++) {
        dayString += this.selectedDay[i];
      }
      this.postData.days = dayString;
      const updateData = {
        study: this.postData,
        tag: this.tagData,
      };
      axios
        .post(SERVER_URL + "/study/daysupdate", updateData)
        .then((res) => {
          this.getDetail();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getPostTime(n) {
      axios
        .get(SERVER_URL + "/diary/list", {
          params: {
            pid: this.$route.params.post_id,
            tmp: 1,
          },
        })
        .then((res) => {
          this.expectTodo = res.data.object[1];
          var today = new Date();
          today.setHours(0, 0, 0, 0);
          var count = new Date(res.data.object[1].dodate);
          count.setHours(0, 0, 0, 0);
          var dday = Math.floor((count - today) / 1000 / 24 / 60 / 60);
          if (dday <= 0) {
            this.decimalDay = "day";
          } else {
            this.decimalDay = dday;
          }
          if (n == 0) {
            this.allDailyLists = res.data.object[0];
            var i = 0;
            const post_id = this.$route.params.post_id;
            const ecolor = [
              "#ACBFEA",
              "#F8DDA9",
              "#FDAF9B",
              "#DCCBED",
              "#B6E3E9",
            ];
            while (i < this.allDailyLists.length) {
              var d = this.allDailyLists[i].title;
              var s = this.allDailyLists[i].posttime;
              var daily_id = this.allDailyLists[i].did;
              const test = {
                title: d,
                date: s,
                color: ecolor[i % 5], // an option!
                textColor: "black", // an option!
                url:
                  "http://i3b205.p.ssafy.io:8081/#/study/"+
                  post_id +
                  "/" +
                  daily_id +
                  "/detail",
              };
              this.calendarOptions.events.push(test);
              i++;
            }
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 다가올 일정
    expectOk() {
      if (this.expectData.dodate == null) {
        alert("날짜가 입력되지 않았습니다.");
      } else if (this.expectData.doplace == "") {
        alert("장소가 입력되지 않았습니다.");
      } else if (this.expectData.assignment == "") {
        alert("과제가 입력되지 않았습니다.");
      } else {
        this.expectData.uid = this.profileInfo.uid;
        this.expectData.pid = this.postData.pid;
        axios
          .post(SERVER_URL + "/upcoming/create", this.expectData)
          .then((res) => {
            this.expectTodo = res.data.object;
            var today = new Date();
            today.setHours(0, 0, 0, 0);
            var count = new Date(res.data.object.dodate);
            count.setHours(0, 0, 0, 0);
            var dday = Math.floor((count - today) / 1000 / 24 / 60 / 60);
            if (dday <= 0) {
              this.decimalDay = "day";
            } else {
              this.decimalDay = dday;
            }
          })
          .catch((err) => console.log(err));
      }
    },
    expectUpdate() {
      this.expectData.eid = this.expectTodo.eid;
      this.expectData.uid = this.profileInfo.uid;
      this.expectData.pid = this.postData.pid;
      if (this.expectData.dodate == null) {
        alert("날짜가 입력되지 않았습니다.");
      } else if (this.expectData.doplace == "") {
        alert("장소가 입력되지 않았습니다.");
      } else if (this.expectData.assignment == "") {
        alert("과제가 입력되지 않았습니다.");
      } else {
        axios
          .post(SERVER_URL + "/upcoming/update", this.expectData)
          .then((res) => {
            this.expectTodo = res.data.object;
            var today = new Date();
            today.setHours(0, 0, 0, 0);
            var count = new Date(res.data.object.dodate);
            count.setHours(0, 0, 0, 0);
            var dday = Math.floor((count - today) / 1000 / 24 / 60 / 60);
            if (dday <= 0) {
              this.decimalDay = "day";
            } else {
              this.decimalDay = dday;
            }
          })

          .catch((err) => console.log(err));
      }
    },
    expectDelete() {
      this.expectData.eid = this.expectTodo.eid;
      this.expectData.uid = this.profileInfo.uid;
      this.expectData.pid = this.postData.pid;
      axios
        .post(SERVER_URL + "/upcoming/delete", this.expectData)
        .then((res) => {
          this.getPostTime(1)
        })
        .catch((err) => console.log(err));
    },
    reportCheck() {
      const pid = this.$route.params.post_id;
      axios
        .post(SERVER_URL + "/account/reportlist?pid=" + pid)
        .then((res) => {
          const tmpList = [];
          for (let i = 0; i < res.data.object.length; i++) {
            tmpList.push(res.data.object[i].target);
          }
          this.reportList = tmpList;
        })
        .catch((err) => console.log(err));
    },
    alertCheck() {
          swal("", "이미 평가한 회원입니다.");
    },
    reportMember() {
      this.reportdata.pid = this.$route.params.post_id;
      this.reportdata.reporter = this.profileInfo.uid;
      axios
        .post(SERVER_URL + "/account/report", this.reportdata)
        .then(() => {
          this.reportCheck();
          (this.reportdata.pid = ""),
          (this.reportdata.reporter = ""),
          (this.reportdata.target = ""),
          (this.reportdata.reason = "");
        })
        .catch((err) => console.log(err));
    },

    // 상호 평가
    setEvaluage() {
      this.evalueData.pid = this.postData.pid;
      this.evalueData.writer_uid = this.profileInfo.uid;
      axios
        .post(SERVER_URL + "/eva/score", this.evalueData)
        .then(() => {
          this.checkEvalue();
          this.evalueData = {
            pid: 0,
            writer_uid: 0,
            target_uid: 0,
            score1: 1,
            score2: 1,
            score3: 1,
            sentence: null,
          };
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkEvalue() {
      let today = new Date();
      let year = today.getFullYear();
      let month = today.getMonth() + 1;
      let date = today.getDate();
      if (month < 10) {
        var d = year + "-0" + month + "-" + date;
      } else {
        var d = year + "-" + month + "-" + date;
      }
      const evaList = {
        pid: this.postData.pid,
        writer_uid: this.profileInfo.uid,
        eva_date: d,
      };
      const alreadyList = [];
      axios
        .post(SERVER_URL + "/eva/list", evaList)
        .then((res) => {
          const tmpData = res.data.object;
          for (let i = 0; i < tmpData.length; i++) {
            alreadyList.push(tmpData[i].targetuid);
          }
          this.alreadyEva = alreadyList;
          console.log(alreadyList);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    onceEva() {
      swal("","이미 평가 하셨습니다.");
    },
    setFinEva() {
      this.evalueData.pid = this.postData.pid;
      this.evalueData.writer_uid = this.profileInfo.uid;
      this.evalueData.count = 1;
      axios
        .post(SERVER_URL + "/eva/write", this.evalueData)
        .then(() => {
          this.checkFinalEva();
          this.evalueData = {
            pid: 0,
            writer_uid: 0,
            target_uid: 0,
            score1: 1,
            score2: 1,
            score3: 1,
            sentence: null,
            count: 0,
          };
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkFinalEva() {
      const evaFinList = {
        pid: this.postData.pid,
        writer_uid: this.profileInfo.uid,
        count: 1,
      };
      const alreadyList = [];
      axios
        .post(SERVER_URL + "/eva/finallist", evaFinList)
        .then((res) => {
          const tmpData = res.data.object;
          for (let i = 0; i < tmpData.length; i++) {
            alreadyList.push(tmpData[i].targetuid);
          }
          this.alreadyEva = alreadyList;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    goDailyCreate() {
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var d = this.week[date.getDay()];
      if (month < 10) {
        month = "0" + month;
      }
      if (day < 10) {
        day = "0" + day;
      }
      var today = year + "-" + month + "-" + day;
      let flag = false;

      for (var i = 0; i < this.allDailyLists.length; i++) {
        if (
          this.allDailyLists[i].posttime === today &&
          this.allDailyLists[i].uid === this.profileInfo.uid
        ) {
          swal("", "일지는 하루에 한번만 작성가능 합니다.");
          flag = true;
          break;
        }
      }
      if (this.days.indexOf(d) == -1) {
        swal("", "일지작성이 불가능한 날입니다.");
        flag = true;
      }
      if (!flag) {
        this.$router.push({
          name: constants.URL_TYPE.STUDY.DAILYCREATE,
          params: {
            post_id: this.$route.params.post_id,
          },
        });
      }
    },
    goBoard(post_id) {
      this.$router.push({
        name: constants.URL_TYPE.BOARD.BOARDMAIN,
        params: {
          post_id: post_id,
        },
      });
    },
    goPostMain(post_id) {
      this.$router.push({
        name: constants.URL_TYPE.POST.POSTDETAIL,
        params: {
          post_id: post_id,
        },
      });
    },
    goMemberProfile(user_id) {
      this.$router.push({
        name: constants.URL_TYPE.USER.MEMBERPROFILE,
        params: {
          post_id: this.$route.params.post_id,
          user_id: user_id,
        },
      });
    },
  },
};
</script>

<style scoped>
.dailyselect {
  position: absolute;
  top: 70px;
  right: 2%;
}
.diarybutton {
  font-family: "Do Hyeon", sans-serif;
  position: absolute;
  top: 62px;
  right: 45%;
}
@media (max-width: 760px) {
  .dailyselect {
    position: absolute;
    top: 25px;
    right: 6%;
    font-size: 0.8rem;
  }
  .diarybutton {
    position: absolute;
    top: 58px;
    right: 16px;
    font-size: 0.6rem;
  }
}
.clickstudy {
  cursor: pointer;
}

.createpoint {
  position: absolute;
  right: 2%;
  cursor: pointer;
  font-size: large;
}

.dailycss:hover {
  cursor: pointer;
  background-color: #eee;
}
</style>

<style>
@media (max-width: 760px) {
  .fc .fc-button-group {
    position: absolute;
    top: 13%;
    left: 38%;
  }
  .fc .fc-button-group > .fc-button {
    color: gray;
    border: 1px solid gray;
    padding: 0 0.2rem;
    background-color: white;
  }
  .fc-direction-ltr .fc-button-group > .fc-button:not(:first-child) {
    background-color: white !important;
    border: 1px solid gray !important;
  }
  .fc-daygrid-block-event .fc-event-time,
  .fc-daygrid-block-event .fc-event-title {
    font-size: 0.5rem;
  }
  .fc .fc-daygrid-day-number {
    font-size: 0.6rem;
  }
  .fc .fc-col-header-cell-cushion {
    font-size: 0.7rem !important;
  }
  .fc-dayGridMonth-button.fc-button.fc-button-primary.fc-button-active,
  .fc-dayGridWeek-button.fc-button.fc-button-primary.fc-button,
  .fc-listMonth-button.fc-button.fc-button-primary.fc-button {
    display: none;
  }
  .fc-daygrid-body.fc-daygrid-body-unbalanced {
    width: 100% !important;
  }
  .fc .fc-view-harness {
    margin: 1rem !important;
  }
  .fc .fc-toolbar-title {
    font-size: 1.2rem;
  }
}
@media (max-width: 480px) {
  .fc .fc-button-group {
    position: absolute;
    top: 17%;
    left: 33%;
  }
  
  .fc .fc-button-group > .fc-button {
    color: gray;
    border: 1px solid gray;
    padding: 0 0.2rem;
    background-color: white;
  }
  .fc-direction-ltr .fc-button-group > .fc-button:not(:first-child) {
    background-color: white !important;
    border: 1px solid gray !important;
  }
  .fc-daygrid-block-event .fc-event-time,
  .fc-daygrid-block-event .fc-event-title {
    font-size: 0.5rem;
  }
  .fc .fc-daygrid-day-number {
    font-size: 0.6rem;
  }
  .fc .fc-col-header-cell-cushion {
    font-size: 0.7rem !important;
  }
  .fc-dayGridMonth-button.fc-button.fc-button-primary.fc-button-active,
  .fc-dayGridWeek-button.fc-button.fc-button-primary.fc-button,
  .fc-listMonth-button.fc-button.fc-button-primary.fc-button {
    display: none;
  }
  .fc-daygrid-body.fc-daygrid-body-unbalanced {
    width: 100% !important;
  }
  .fc .fc-view-harness {
    margin: 1rem !important;
  }
  .fc .fc-toolbar-title {
    font-size: 1.2rem;
  }
}
</style>