<template>
  <a-modal v-model="show" title="技术难点推荐求职者" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="recommendData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">技术信息</span></a-col>
        <a-col :span="8"><b>技术难点：</b>
          {{ recommendData.name }}
        </a-col>
        <a-col :span="8"><b>技术类型：</b>
          {{ recommendData.type }}
        </a-col>
        <a-col :span="8"><b>内容：</b>
          {{ recommendData.content }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-collapse default-active-key="1" :bordered="false" v-if="expertList.length !== 0">
        <a-collapse-panel :header="item.name" :style="customStyle" v-for="(item, index) in expertList" :key="index">
          <div style="font-size: 13px;font-family: SimHei">
            <a-row style="padding-left: 24px;padding-right: 24px;">
              <a-col :span="8"><b>政治面貌：</b>
                {{ item.politicalStatus }}
              </a-col>
              <a-col :span="8"><b>籍贯：</b>
                {{ item.nativePlace }}
              </a-col>
              <a-col :span="8"><b>工作单位：</b>
                {{ item.employer }}
              </a-col>
            </a-row>
            <br/>
            <a-row style="padding-left: 24px;padding-right: 24px;">
              <a-col :span="8"><b>职务：</b>
                {{ item.position }}
              </a-col>
              <a-col :span="16"><b>通讯地址：</b>
                {{ item.address }}
              </a-col>
              <br/>
              <br/>
              <a-col :span="8"><b>手机号：</b>
                {{ item.phone }}
              </a-col>
              <a-col :span="8"><b>专业方向一级：</b>
                {{ item.levelOne }}
              </a-col>
            </a-row>
            <a-row style="padding-left: 24px;padding-right: 24px;">
              <br/>
              <a-col :span="8"><b>出生日期：</b>
                {{ item.birthDate }}
              </a-col>
              <a-col :span="16"><b>专业方向二级：</b>
                {{ item.levelTwo }}
              </a-col>
            </a-row>
            <br/>
            <a-row style="padding-left: 24px;padding-right: 24px;">
              <a-col :span="8"><b>职称：</b>
                {{ item.jobTitle }}
              </a-col>
              <a-col :span="8"><b>特殊称谓：</b>
                {{ item.specialAppellation }}
              </a-col>
              <a-col :span="8"><b>邮箱：</b>
                {{ item.mail }}
              </a-col>
            </a-row>
          </div>
        </a-collapse-panel>
      </a-collapse>
      <div v-else style="font-size: 25px;margin: 25px;text-align: center">
        暂无推荐求职者
      </div>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
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
  name: 'recommendView',
  props: {
    recommendShow: {
      type: Boolean,
      default: false
    },
    recommendData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.recommendShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      customStyle: 'background: #f7f7f7;border-radius: 4px;margin-bottom: 24px;border: 0;overflow: hidden',
      expertList: []
    }
  },
  watch: {
    recommendShow: function (value) {
      if (value) {
        this.selectExpertList(this.recommendData.type)
      }
    }
  },
  methods: {
    selectExpertList (key) {
      this.$get(`/cos/expert-info/key/list/${key}`).then((r) => {
        this.expertList = r.data.data
      })
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
