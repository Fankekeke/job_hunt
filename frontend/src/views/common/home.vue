<template>
  <a-row :gutter="20" style="width: 81%;margin: 0 auto;font-family: SimHei">
    <a-col :span="18">
      <a-row style="margin-top: 15px">
<!--        <a-col :span="24">-->
<!--          <a-col style="margin-bottom: 15px">-->
<!--            <i style="font-size: 23px;color: #000c17">求职者</i>-->
<!--            <a style="font-size:13px;margin-left: 15px" @click="$router.push('/exview')">-> 更多</a>-->
<!--          </a-col>-->
<!--          <a-col>-->
<!--            <a-row :gutter="15">-->
<!--              <a-col span="8" v-for="(item, index) in expertList" :key="index" style="margin-bottom: 15px">-->
<!--                <a-card hoverable>-->
<!--                  <template slot="actions" class="ant-card-actions">-->
<!--                    <a-icon key="cloud" type="cloud" @click="handleExpertProductOpen(item)"/>-->
<!--                    <a-icon key="ellipsis" type="ellipsis" @click="handleExpertViewOpen(item)"/>-->
<!--                  </template>-->
<!--                  <a-card-meta>-->
<!--                    <span slot="title">-->
<!--                      {{ item.name }} <span style="font-size: 12px"> - {{ item.position }}</span>-->
<!--                    </span>-->
<!--                    <span slot="description">-->
<!--                      <span style="font-size: 12px">专业一级: {{ item.levelOne }} - 专业二级: {{ item.levelTwo }}</span>-->
<!--                      <div style="margin-top: 5px;font-size: 12px">-->
<!--                        {{ item.profile != null ? item.profile.slice(0, 30) + '...' : ' - - ' }}-->
<!--                      </div>-->
<!--                    </span>-->
<!--                    <a-avatar-->
<!--                      slot="avatar"-->
<!--                      :src="'http://127.0.0.1:9527/imagesWeb/' + item.images"-->
<!--                    />-->
<!--                  </a-card-meta>-->
<!--                </a-card>-->
<!--              </a-col>-->
<!--            </a-row>-->
<!--          </a-col>-->
<!--        </a-col>-->
        <a-col :span="24" style="margin-top: 20px">
          <a-col style="margin-bottom: 15px">
            <i style="font-size: 23px;color: #000c17">岗位</i>
            <a style="font-size:13px;margin-left: 15px" @click="$router.push('/expert/post')">-> 更多</a>
          </a-col>
          <a-col>
            <a-row :gutter="15">
              <a-col span="8" v-for="(item, index) in postList" :key="index" style="margin-bottom: 15px">
                <a-card :bordered="false" hoverable @click="postDetail(item)">
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
                </a-card>
              </a-col>
            </a-row>
          </a-col>
        </a-col>
        <a-col :span="24" style="margin-top: 20px">
          <a-col style="margin-bottom: 15px">
            <i style="font-size: 23px;color: #000c17">兼职</i>
            <a style="font-size:13px;margin-left: 15px" @click="$router.push('/expert/pluralism')">-> 更多</a>
          </a-col>
          <a-col>
            <a-row :gutter="15">
              <a-col span="8" v-for="(item, index) in pluralismList" :key="index" style="margin-bottom: 15px">
                <a-card :bordered="false" hoverable @click="pluralismDetail(item)">
                  <a-card-meta style="margin-top: 10px">
                    <template slot="title">
                      {{ item.title }} - {{ item.postName }}
                    </template>
                    <template slot="description">
                      {{ item.workDescribe.slice(0, 60) }}...
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
                </a-card>
              </a-col>
            </a-row>
          </a-col>
        </a-col>
        <a-col :span="24" style="margin-top: 20px">
          <a-col style="margin-bottom: 15px">
            <i style="font-size: 23px;color: #000c17">企业</i>
            <a style="font-size:13px;margin-left: 15px" @click="$router.push('/epview')">-> 更多</a>
          </a-col>
          <a-col>
            <a-row :gutter="15">
              <a-col span="8" v-for="(item, index) in enterpriseList" :key="index" style="margin-bottom: 15px">
                <a-card hoverable>
                  <template slot="actions" class="ant-card-actions">
                    <a-icon key="ellipsis" type="ellipsis" @click="handleEnterpriseViewOpen(item)"/>
                  </template>
                  <a-card-meta>
                    <span slot="title">
                      {{ item.name }} <span style="font-size: 12px"> - {{ item.nature }}</span>
                    </span>
                    <span slot="description">
                      <span style="font-size: 12px"> {{ item.industry }} - {{ item.status }}</span>
                      <div style="margin-top: 5px;font-size: 12px">
                        {{ item.unitDescription != null ? item.unitDescription.slice(0, 30) + '...' : ' - - ' }}
                      </div>
                    </span>
                    <a-avatar
                      slot="avatar"
                      :src="'http://127.0.0.1:9527/imagesWeb/' + item.images"
                    />
                  </a-card-meta>
                </a-card>
              </a-col>
            </a-row>
          </a-col>
        </a-col>
      </a-row>
    </a-col>
    <a-col :span="6">
      <a-card hoverable :bordered="false" title="公告信息" style="height: 750px;overflow: auto">
        <div style="padding: 0 22px">
          <a-list item-layout="vertical" :pagination="false" :data-source="bulletinList">
            <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
              <template slot="actions">
              <span key="message">
                <a-icon type="message" style="margin-right: 8px" />
                {{ item.date }}
              </span>
              </template>
              <a-list-item-meta :description="item.content" style="font-size: 14px">
                <a slot="title">{{ item.title }}</a>
              </a-list-item-meta>
            </a-list-item>
          </a-list>
        </div>
      </a-card>
    </a-col>
    <expert-view
      @close="handleExpertViewClose"
      :expertShow="expertView.visiable"
      :expertData="expertView.data">
    </expert-view>
    <enterprise-view
      @close="handleEnterpriseViewClose"
      :enterpriseShow="enterpriseView.visiable"
      :enterpriseData="enterpriseView.data">
    </enterprise-view>
    <expert-product-view
      @close="handleExpertProductClose"
      :expertShow="expertProduct.visiable"
      :expertData="expertProduct.data">
    </expert-product-view>
    <pluralism-view :pluralismShow="pluralismView.visiable" :pluralismData="pluralismView.data" @close="pluralismView.visiable = false"></pluralism-view>
    <post-view :pluralismShow="postView.visiable" :pluralismData="postView.data" @close="postView.visiable = false"></post-view>
  </a-row>
</template>

<script>
import ExpertView from '../admin/expert/ExpertView.vue'
import ExpertProductView from '../admin/expert/ExpertProductView.vue'
import EnterpriseView from '../admin/enterprise/EnterpriseView.vue'
import PluralismView from '../admin/pluralism/PluralismView.vue'
import PostView from '../admin/post/PostView.vue'

export default {
  name: 'home',
  components: {EnterpriseView, ExpertView, ExpertProductView, PluralismView, PostView},
  data () {
    return {
      bulletinList: [],
      expertList: [],
      enterpriseList: [],
      postList: [],
      pluralismList: [],
      pluralismView: {
        visiable: false,
        data: null
      },
      postView: {
        visiable: false,
        data: null
      },
      expertView: {
        visiable: false,
        data: null
      },
      expertProduct: {
        visiable: false,
        data: null
      },
      enterpriseView: {
        visiable: false,
        data: null
      }
    }
  },
  mounted () {
    this.getBulletinData()
    this.getExpertData()
    this.getEnterpriseData()
    this.selectPluralismList()
    this.selectPostList()
  },
  methods: {
    postDetail (row) {
      this.postView.visiable = true
      this.postView.data = row
    },
    pluralismDetail (row) {
      this.pluralismView.visiable = true
      this.pluralismView.data = row
    },
    selectPluralismList (params = {}) {
      params.delFlag = 1
      params.size = 3
      this.$get('/cos/pluralism-info/page', {...params}).then((r) => {
        this.pluralismList = r.data.data.records
      })
    },
    selectPostList (params = {}) {
      params.delFlag = 1
      params.size = 3
      this.$get('/cos/post-info/page', {...params}).then((r) => {
        this.postList = r.data.data.records
      })
    },
    getBulletinData () {
      this.$get(`/cos/bulletin-info/list`).then((r) => {
        this.bulletinList = r.data.data
      })
    },
    getExpertData (key) {
      if (key === undefined || key === null) {
        key = ''
      }
      this.$get(`/cos/expert-info/key/list/${key}`).then((r) => {
        this.expertList = r.data.data
      })
    },
    getEnterpriseData (key) {
      if (key === undefined || key === null) {
        key = ''
      }
      this.$get(`/cos/enterprise-info/list/${key}`).then((r) => {
        this.enterpriseList = r.data.data
      })
    },
    handleExpertViewOpen (record) {
      this.expertView.data = record
      this.expertView.visiable = true
    },
    handleExpertViewClose () {
      this.expertView.visiable = false
    },
    handleExpertProductOpen (record) {
      this.expertProduct.data = record
      this.expertProduct.visiable = true
    },
    handleExpertProductClose () {
      this.expertProduct.visiable = false
    },
    handleEnterpriseViewOpen (record) {
      this.enterpriseView.data = record
      this.enterpriseView.visiable = true
    },
    handleEnterpriseViewClose () {
      this.enterpriseView.visiable = false
    }
  }
}
</script>

<style scoped>

</style>
