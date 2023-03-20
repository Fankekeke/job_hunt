<template>
  <div :class="[multipage === true ? 'multi-page':'single-page', 'not-menu-page', 'home-page']" style="background-color: #f0f2f5; border: none">
    <a-row :gutter="20" style="width: 81%;margin: 0 auto;margin-bottom: 15px">
      <a-col :span="18">
        <a-carousel effect="fade">
          <div style="width: 100%;height: 550px"><img :src="'http://127.0.0.1:9527/imagesWeb/1.jpg'" style="width: 100%;height: 100%;object-fit:cover;" /></div>
          <div style="width: 100%;height: 550px"><img :src="'http://127.0.0.1:9527/imagesWeb/2.jpg'" style="width: 100%;height: 100%;object-fit:cover;" /></div>
          <div style="width: 100%;height: 550px"><img :src="'http://127.0.0.1:9527/imagesWeb/3.jpg'" style="width: 100%;height: 100%;object-fit:cover;" /></div>
          <div style="width: 100%;height: 550px"><img :src="'http://127.0.0.1:9527/imagesWeb/4.jpg'" style="width: 100%;height: 100%;object-fit:cover;" /></div>
        </a-carousel>
      </a-col>
      <a-col :span="6">
        <a-card hoverable :loading="loading" :bordered="false" title="消息通知" style="height: 550px;overflow: auto">
          <div style="padding: 0 22px">
            <a-list item-layout="vertical" :pagination="false" :data-source="bulletinList">
              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                <template slot="actions">
                  <span key="message" style="font-size: 13px">
                    <a-icon type="message" style="margin-right: 8px" />
                    {{ item.createDate }}
                  </span>
                </template>
                <a-list-item-meta :description="item.content" style="font-size: 13px">
                </a-list-item-meta>
                <a slot="actions">
                  <span @click="cleanMessage(item.id)">已阅</span>
                </a>
              </a-list-item>
            </a-list>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="8" class="head-info">
      <a-card class="head-info-card" style="width: 80%;margin: 0 auto">
        <a-col :span="12">
          <div class="head-info-avatar">
            <img alt="头像" :src="avatar">
          </div>
          <div class="head-info-count">
            <div class="head-info-welcome">
              {{welcomeMessage}}
            </div>
            <div class="head-info-desc">
              <p>{{user.roleName ? user.roleName : '暂无角色'}}</p>
            </div>
            <div class="head-info-time">上次登录时间：{{user.lastLoginTime ? user.lastLoginTime : '第一次访问系统'}}</div>
          </div>
        </a-col>
        <a-col :span="12">
          <div>
            <a-row class="more-info">
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4">
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-card>
    </a-row>
    <a-row :gutter="8" class="count-info">
      <a-card class="head-info-card" style="width: 80%;margin: 0 auto"></a-card>
    </a-row>
    <home></home>
  </div>
</template>
<script>
import HeadInfo from '@/views/common/HeadInfo'
import {mapState} from 'vuex'
import moment from 'moment'
import Home from './common/home.vue'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

export default {
  name: 'HomePage',
  components: {Home, HeadInfo},
  data () {
    return {
      loading: false,
      newsPage: 0,
      newsContent: '',
      newsList: [],
      todayIp: '',
      todayVisitCount: '',
      totalVisitCount: '',
      userRole: '',
      userDept: '',
      lastLoginTime: '',
      welcomeMessage: '',
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 2
      },
      bulletinList: []
    }
  },
  watch: {
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    }),
    avatar () {
      return `static/avatar/${this.user.avatar}`
    }
  },
  methods: {
    cleanMessage (id) {
      this.$get(`/cos/notify-info/setReadStatus/${id}`).then((r) => {
        this.expertList = r.data.data
        this.getNewList()
      })
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    handleCancel () {
      this.previewVisible = false
    },
    welcome () {
      const date = new Date()
      const hour = date.getHours()
      let time = hour < 6 ? '早上好' : (hour <= 11 ? '上午好' : (hour <= 13 ? '中午好' : (hour <= 18 ? '下午好' : '晚上好')))
      return `${time}，${this.user.username}`
    },
    getNewList () {
      this.$get(`/cos/notify-info/list/${this.user.userCode}`).then((r) => {
        this.bulletinList = r.data.data
        console.log(JSON.stringify(this.bulletinList))
      })
    }
  },
  mounted () {
    console.log(JSON.stringify(this.user))
    this.getNewList()
    this.welcomeMessage = this.welcome()
    this.$get(`index/${this.user.username}`).then((r) => {
      let data = r.data.data
      this.todayIp = data.todayIp
      this.todayVisitCount = data.todayVisitCount
      this.totalVisitCount = data.totalVisitCount
      let sevenVisitCount = []
      let dateArr = []
      for (let i = 6; i >= 0; i--) {
        let time = moment().subtract(i, 'days').format('MM-DD')
        let contain = false
        for (let o of data.lastSevenVisitCount) {
          if (o.days === time) {
            contain = true
            sevenVisitCount.push(o.count)
          }
        }
        if (!contain) {
          sevenVisitCount.push(0)
        }
        dateArr.push(time)
      }
      let sevenUserVistCount = []
      for (let i = 6; i >= 0; i--) {
        let time = moment().subtract(i, 'days').format('MM-DD')
        let contain = false
        for (let o of data.lastSevenUserVisitCount) {
          if (o.days === time) {
            contain = true
            sevenUserVistCount.push(o.count)
          }
        }
        if (!contain) {
          sevenUserVistCount.push(0)
        }
      }
    }).catch((r) => {
      console.error(r)
      this.$message.error('获取首页信息失败')
    })
  }
}
</script>
<style lang="less">
  .home-page {
    .head-info {
      margin-bottom: .5rem;
      .head-info-card {
        padding: .5rem;
        border-color: #f1f1f1;
        .head-info-avatar {
          display: inline-block;
          float: left;
          margin-right: 1rem;
          img {
            width: 5rem;
            border-radius: 2px;
          }
        }
        .head-info-count {
          display: inline-block;
          float: left;
          .head-info-welcome {
            font-size: 1.05rem;
            margin-bottom: .1rem;
          }
          .head-info-desc {
            color: rgba(0, 0, 0, 0.45);
            font-size: .8rem;
            padding: .2rem 0;
            p {
              margin-bottom: 0;
            }
          }
          .head-info-time {
            color: rgba(0, 0, 0, 0.45);
            font-size: .8rem;
            padding: .2rem 0;
          }
        }
      }
    }
    .count-info {
      .visit-count-wrapper {
        padding-left: 0 !important;
        .visit-count {
          padding: .5rem;
          border-color: #f1f1f1;
          .ant-card-body {
            padding: .5rem 1rem !important;
          }
        }
      }
      .project-wrapper {
        padding-right: 0 !important;
        .project-card {
          border: none !important;
          .ant-card-head {
            border-left: 1px solid #f1f1f1 !important;
            border-top: 1px solid #f1f1f1 !important;
            border-right: 1px solid #f1f1f1 !important;
          }
          .ant-card-body {
            padding: 0 !important;
            table {
              width: 100%;
              td {
                width: 50%;
                border: 1px solid #f1f1f1;
                padding: .6rem;
                .project-avatar-wrapper {
                  display:inline-block;
                  float:left;
                  margin-right:.7rem;
                  .project-avatar {
                    color: #42b983;
                    background-color: #d6f8b8;
                  }
                }
              }
            }
          }
          .project-detail {
            display:inline-block;
            float:left;
            text-align:left;
            width: 78%;
            .project-name {
              font-size:.9rem;
              margin-top:-2px;
              font-weight:600;
            }
            .project-desc {
              color:rgba(0, 0, 0, 0.45);
              p {
                margin-bottom:0;
                font-size:.6rem;
                white-space:normal;
              }
            }
          }
        }
      }
    }
  }
</style>
