<template>
  <a-card style="width: 63%;margin: 0 auto;padding-top: 30px">
    <i style="font-size: 23px;margin: 30px;">企业列表</i>
    <div style="margin: 30px">
      <a-input-search placeholder="input search text" style="width: 300px;float: right" @search="onSearch"/>
      <a-list
        style="margin-top: 15px;clear: both"
        class="demo-loadmore-list"
        :loading="loading"
        item-layout="horizontal"
        :data-source="enterpriseList"
      >
        <a-list-item slot="renderItem" slot-scope="item, index">
          <a slot="actions" v-if="user.roleId == 75" @click="chat(item)">沟通</a>
          <a slot="actions" @click="handleEnterpriseViewOpen(item)">详情</a>
          <a-list-item-meta>
            <div slot="description" style="font-family: SimHei;font-size: 14px;margin-top: 20px">
              <a-row :gutter="20">
                <a-col :span="6"><span>企业名称：</span>
                  {{ item.name }}
                </a-col>
                <a-col :span="6"><span>单位简称或代号：</span>
                  {{ item.abbreviation }}
                </a-col>
                <a-col :span="6"><span>统一社会信用代码：</span>
                  {{ item.creditCode }}
                </a-col>
                <a-col :span="6"><span>单位性质：</span>
                  {{ item.nature }}
                </a-col>
                <br/>
                <br/>
                <a-col :span="6"><span>二级企业单位性质：</span>
                  {{ item.natureTwo }}
                </a-col>
                <a-col :span="6"><span>经营状态：</span>
                  {{ item.status }}
                </a-col>
                <a-col :span="6"><span>法定代表人：</span>
                  {{ item.corporateRepresentative }}
                </a-col>
                <a-col :span="6"><span>注册资本（万元）：</span>
                  {{ item.registeredCapital }}
                </a-col>
                <br/>
                <br/>
              </a-row>
            </div>
            <a slot="title" style="font-size: 20px;font-family: SimHei">{{ item.name }}</a>
            <a-avatar
              shape="square"
              :size="126"
              icon="user"
              slot="avatar"
              :src="'http://127.0.0.1:9527/imagesWeb/'+item.images"
            />
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    </div>
    <enterprise-view
      @close="handleEnterpriseViewClose"
      :enterpriseShow="enterpriseView.visiable"
      :enterpriseData="enterpriseView.data">
    </enterprise-view>
  </a-card>
</template>

<script>
import EnterpriseView from '../admin/enterprise/EnterpriseView.vue'
import {mapState} from 'vuex'
export default {
  name: 'Enterprise',
  components: {EnterpriseView},
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  },
  data () {
    return {
      enterpriseView: {
        visiable: false,
        data: null
      },
      loading: false,
      data: [],
      enterpriseList: []
    }
  },
  mounted () {
    this.getEnterpriseList()
  },
  methods: {
    chat (item) {
      this.$post(`/cos/chat-info`, {
        expertCode: this.user.userCode,
        enterpriseCode: item.code,
        type: 1,
        content: '你好'
      }).then((r) => {
        this.$router.push('/expert/chat')
      })
    },
    onSearch (value) {
      this.getEnterpriseList(value)
    },
    handleEnterpriseViewOpen (record) {
      this.enterpriseView.data = record
      this.enterpriseView.visiable = true
    },
    handleEnterpriseViewClose () {
      this.enterpriseView.visiable = false
    },
    getEnterpriseList (key) {
      if (key === undefined || key === null) {
        key = ''
      }
      this.$get(`/cos/enterprise-info/list/${key}`).then((r) => {
        this.enterpriseList = r.data.data
      })
    }
  }
}
</script>

<style scoped>

</style>
