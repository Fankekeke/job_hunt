<template>
  <a-row :gutter="20">
    <a-col :span="16">
      <a-card :loading="loading" :bordered="false">
        <a-form :form="form" layout="vertical">
          <a-row :gutter="20">
            <a-col :span="12">
              <a-form-item label='求职者姓名' v-bind="formItemLayout">
                <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入求职者姓名!' }] }
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='民族' v-bind="formItemLayout">
                <a-input v-decorator="[
            'nationality'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='性别' v-bind="formItemLayout">
                <a-input v-decorator="[
            'sex'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='政治面貌' v-bind="formItemLayout">
                <a-input v-decorator="[
            'politicalStatus'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='籍贯' v-bind="formItemLayout">
                <a-input v-decorator="[
            'nativePlace'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='工作单位' v-bind="formItemLayout">
                <a-input v-decorator="[
            'employer'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='职务' v-bind="formItemLayout">
                <a-input v-decorator="[
            'position'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='通讯地址' v-bind="formItemLayout">
                <a-input v-decorator="[
            'address'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='手机号' v-bind="formItemLayout">
                <a-input v-decorator="[
            'phone'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='专业方向一级' v-bind="formItemLayout">
                <a-input v-decorator="[
            'levelOne'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='专业方向二级' v-bind="formItemLayout">
                <a-input v-decorator="[
            'levelTwo'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='出生日期' v-bind="formItemLayout">
                <a-input v-decorator="[
            'birthDate'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='职称' v-bind="formItemLayout">
                <a-input v-decorator="[
            'jobTitle'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='特殊称谓' v-bind="formItemLayout">
                <a-input v-decorator="[
            'specialAppellation'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='邮箱' v-bind="formItemLayout">
                <a-input v-decorator="[
            'mail'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='固定电话' v-bind="formItemLayout">
                <a-input v-decorator="[
            'fixedTelephone'
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label='个人简介' v-bind="formItemLayout">
                <a-textarea :rows="6" v-decorator="[
            'profile',
             { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label='求职者头像' v-bind="formItemLayout">
                <a-upload
                  name="avatar"
                  action="http://127.0.0.1:9527/file/fileUpload/"
                  list-type="picture-card"
                  :file-list="fileList"
                  @preview="handlePreview"
                  @change="picHandleChange"
                >
                  <div v-if="fileList.length < 1">
                    <a-icon type="plus" />
                    <div class="ant-upload-text">
                      Upload
                    </div>
                  </div>
                </a-upload>
                <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
                  <img alt="example" style="width: 100%" :src="previewImage" />
                </a-modal>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
        <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
          修改
        </a-button>
      </a-card>
    </a-col>
    <a-col :span="8"></a-col>
  </a-row>
</template>

<script>
import {mapState} from 'vuex'

function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'User',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      form: this.$form.createForm(this),
      formItemLayout,
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      expertInfo: null
    }
  },
  mounted () {
    this.getExpertInfo(this.currentUser.userCode)
  },
  methods: {
    getExpertInfo (expertCode) {
      this.$get(`/cos/expert-info/detail/code/${expertCode}`).then((r) => {
        this.expertInfo = r.data.data
        console.log(this.expertInfo)
        this.setFormValues(this.expertInfo)
      })
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues ({...expert}) {
      this.rowId = expert.id
      let fields = ['name', 'nationality', 'sex', 'politicalStatus', 'nativePlace', 'employer', 'position', 'address', 'phone', 'levelOne', 'levelTwo', 'birthDate', 'jobTitle', 'specialAppellation', 'mail', 'fixedTelephone', 'profile']
      let obj = {}
      Object.keys(expert).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(expert['images'])
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = expert[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/cos/expert-info', {
            ...values
          }).then((r) => {
            this.$message.success('更新成功')
            this.loading = false
            this.getExpertInfo(this.currentUser.userCode)
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
