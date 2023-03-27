<template>
  <a-card style="width: 63%;margin: 0 auto;padding-top: 30px">
    <i style="font-size: 23px;margin: 30px;">求职者列表</i>
    <div style="margin: 30px">
      <a-input-search placeholder="input search text" style="width: 300px;float: right" @search="onSearch"/>
      <a-list
        style="margin-top: 15px;clear: both"
        class="demo-loadmore-list"
        :loading="loading"
        item-layout="horizontal"
        :data-source="expertList"
      >
        <a-list-item slot="renderItem" slot-scope="item, index">
          <a slot="actions" v-if="user.roleId == 76" @click="collect(item)">收藏</a>
          <a slot="actions" v-if="user.roleId == 76" @click="chat(item)">沟通</a>
          <a slot="actions" @click="detailView(item)">详情</a>
          <a-list-item-meta>
            <div slot="description" style="font-family: SimHei;font-size: 14px;margin-top: 20px">
              <a-row :gutter="20">
                <a-col :span="6"><span>民族：</span>
                  {{ item.nationality }}
                </a-col>
                <a-col :span="6"><span>性别：</span>
                  {{ item.sex }}
                </a-col>
                <a-col :span="6"><span>政治面貌：</span>
                  {{ item.politicalStatus }}
                </a-col>
                <a-col :span="6"><span>专业方向一级：</span>
                  {{ item.levelOne }}
                </a-col>
                <br/>
                <br/>
                <a-col :span="6"><span>籍贯：</span>
                  {{ item.nativePlace }}
                </a-col>
                <a-col :span="6"><span>工作单位：</span>
                  {{ item.employer }}
                </a-col>
                <a-col :span="6"><span>职务：</span>
                  {{ item.position }}
                </a-col>
                <a-col :span="6"><span>专业方向二级：</span>
                  {{ item.levelTwo }}
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
    <expert-view
      @close="handleExpertViewClose"
      :expertShow="expertView.visiable"
      :expertData="expertView.data">
    </expert-view>
  </a-card>
</template>

<script>
import ExpertView from '../admin/expert/ExpertProductView.vue'
import {mapState} from 'vuex'
export default {
  name: 'Expert',
  components: {ExpertView},
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  },
  data () {
    return {
      expertView: {
        visiable: false,
        data: null
      },
      loading: false,
      data: [],
      expertList: []
    }
  },
  mounted () {
    console.log(this.user)
    this.getEnterpriseList()
  },
  methods: {
    collect (item) {
      this.$get(`/cos/collect-info/collectExpert/check`, {
        enterpriseCode: this.user.userCode,
        expertCode: item.code
      }).then((r) => {
        if (r.data.data === false) {
          this.$message.warn('该求职者已在收藏列表中')
        } else {
          this.$message.success('收藏成功')
        }
      })
    },
    chat (item) {
      this.$post(`/cos/chat-info`, {
        expertCode: item.code,
        enterpriseCode: this.user.userCode,
        type: 2,
        content: '你好'
      }).then((r) => {
        this.$router.push('/enterprise/chat')
      })
    },
    onSearch (value) {
      this.getEnterpriseList(value)
    },
    detailView (value) {
      this.expertView.data = value
      this.expertView.visiable = true
    },
    handleExpertViewClose () {
      this.expertView.visiable = false
    },
    getEnterpriseList (key) {
      if (key === undefined || key === null) {
        key = ''
      }
      this.$get(`/cos/expert-info/key/list/${key}`).then((r) => {
        this.expertList = r.data.data
      })
    }
  }
}
</script>

<style scoped>

</style>
