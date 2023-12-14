<template>
  <a-card style="width: 75%;margin: 0 auto;padding: 30px">
    <a-row :gutter="20" style="width: 100%;margin-top: 30px">
      <a-col>
        <a-form layout="horizontal">
          <a-row :gutter="15">
            <div>
              <a-col :md="6" :sm="24">
                <a-form-item
                  label="企业名称"
                  :labelCol="{span: 4}"
                  :wrapperCol="{span: 18, offset: 2}">
                  <a-input v-model="queryParams.enterpriseName"/>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item
                  label="工作地点"
                  :labelCol="{span: 4}"
                  :wrapperCol="{span: 18, offset: 2}">
                  <a-input v-model="queryParams.address"/>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item
                  label="岗位名称"
                  :labelCol="{span: 4}"
                  :wrapperCol="{span: 18, offset: 2}">
                  <a-input v-model="queryParams.postName"/>
                </a-form-item>
              </a-col>
            </div>
            <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
          </span>
          </a-row>
        </a-form>
      </a-col>
      <div v-if="rentList.length === 0" style="font-size: 25px;text-align: center;margin-top: 85px;font-family: SimHei;">未找到符合岗位信息</div>
      <a-col :span="6" v-for="(item, index) in rentList" :key="index">
        <div style="width: 100%;margin-bottom: 15px;text-align: left;box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;">
          <a-card :bordered="false" hoverable>
            <a-card-meta style="margin-top: 10px">
              <template slot="title">
                {{ item.address }} - {{ item.postName }}
              </template>
              <template slot="description">
                {{ item.responsibility.slice(0, 60) }}...
              </template>
            </a-card-meta>
            <div style="font-size: 12px;font-family: SimHei;margin-top: 15px">
              <span>{{ item.welfare }}</span> |
              <span  style="margin-left: 2px">{{ item.industryName }}</span> |
              <span style="margin-left: 2px">{{ item.workHour }}</span> |
              <span style="color: #f5222d; font-size: 13px;float: right">{{ item.salary }}</span>
            </div>
            <div style="font-size: 13px;font-family: SimHei;margin-top: 15px">
              <a-avatar shape="square" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + item.images"/>
              <b style="margin-left: 5px">{{ item.enterpriseName }}</b>
              <a @click="chat(item)">【联系】</a>
            </div>
            <template slot="actions" class="ant-card-actions">
              <a-icon key="folder-add" type="folder-add" @click="sendInter(item)">投递</a-icon>
              <a-icon key="contacts" type="contacts" @click="rentDetail(item)">详情</a-icon>
              <a-icon key="heart" type="heart" @click="sendCollect(item)">收藏</a-icon>
            </template>
          </a-card>
        </div>
      </a-col>
      <rent-view :pluralismShow="rentView.visiable" :pluralismData="rentView.data" @close="rentView.visiable = false"></rent-view>
    </a-row>
  </a-card>
</template>

<script>
import RentView from '../../admin/post/PostView.vue'
import {mapState} from 'vuex'
export default {
  name: 'House',
  components: {RentView},
  data () {
    return {
      rentList: [],
      rentView: {
        visiable: false,
        data: null
      },
      queryParams: {}
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  mounted () {
    this.selectRentList()
  },
  methods: {
    chat (item) {
      this.$post(`/cos/chat-info`, {
        expertCode: this.currentUser.userCode,
        enterpriseCode: item.enterpriseCode,
        type: 1,
        content: '你好'
      }).then((r) => {
        this.$router.push('/expert/chat')
      })
    },
    search () {
      this.selectRentList({
        ...this.queryParams
      })
    },
    rentDetail (row) {
      this.rentView.visiable = true
      this.rentView.data = row
    },
    sendCollect (row) {
      this.$post('/cos/collect-info', {
        expertCode: this.currentUser.userId,
        baseId: row.id,
        type: 3,
        enterpriseId: row.enterpriseId
      }).then((r) => {
        this.$message.success('收藏成功')
      })
    },
    sendInter (row) {
      this.$post('/cos/interview-info', {
        expertId: this.currentUser.userId,
        baseId: row.id,
        type: 2,
        enterpriseId: row.enterpriseId
      }).then((r) => {
        this.$message.success('投递成功')
      })
    },
    selectRentList (params = {}) {
      params.delFlag = 1
      this.$get('/cos/post-info/page', {...params}).then((r) => {
        this.rentList = r.data.data.records
        console.log(this.rentList)
      })
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 250px;
  line-height: 250px;
  overflow: hidden;
}
</style>
