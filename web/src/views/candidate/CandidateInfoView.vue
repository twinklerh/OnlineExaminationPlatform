<template>
    <div class="container">
        <div class="content-wrapper">
            <img class="display-heading" :src="headImage" @click="uploadVisable = true">
            <span style="width: 15px;"></span>
            <div class="text-fullname">{{ candidate.fullname }}</div>
        </div><br>
        <div class="font-style">出生日期：
            <el-input v-model="candidate.birthday" class="input-msg" placeholder="yyyy-mm-dd"/>
        </div><br>
        <div class="font-style">性别：
            <el-select v-model="candidate.sex" class="input-msg">
                <el-option label="男" value="m"></el-option>
                <el-option label="女" value="f"></el-option>
            </el-select>
        </div><br>
        <div class="font-style">邮箱：<el-input v-model="candidate.email" class="input-msg" /></div><br>
        <div class="font-style">电话：<el-input v-model="candidate.telephone" class="input-msg" /></div><br>
        <br><br><el-button type="primary" style="margin-left: 405px;" @click="setInformation">提交</el-button>
    </div>
    <el-dialog title="更改头像" v-model="uploadVisable">
        <el-upload
            class="upload-demo" list-type="picture-card" 
            action="http://127.0.0.1:3000/candidate/upload/headImage/"
            :file-list="files"
            :headers="{Authorization: 'Bearer ' + useUserStore().token}"
            :limit="1"
            :on-exceed="() => {ElMessage.error('一次只能上传一张图片')}"
            accept=".jpg, .png"
            :on-success="setHeadImage"
            :before-upload="handleBeforeUpload"
        >
            <el-icon><Plus /></el-icon>            
        </el-upload>
    </el-dialog>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { ElMessage, UploadFile, UploadFiles, UploadRawFile } from 'element-plus';
import $ from 'jquery';
import { Plus } from '@element-plus/icons-vue'
import { ref } from 'vue';
import defaultHeadImg from '@/assets/defaultHeadImg.png';
const candidate = ref({fullname: '', sex: '', email: '', telephone: '', birthday: ''});
const uploadVisable = ref(false);
const files = ref([]);
const headImage = ref(defaultHeadImg);

function getInformation()    {
    $.ajax({
        url: 'http://127.0.0.1:3000/get/candidate/information/',
        type: 'get',
        headers: {
            Authorization: "Bearer " + useUserStore().token,
        },
        success: (result:string) => {
            const resp = JSON.parse(result);
            candidate.value = JSON.parse(resp.candidate);
        }
    })
}

function setInformation()   {
    $.ajax({
        url: 'http://127.0.0.1:3000/candidate/set/information/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + useUserStore().token,
        },
        data: {
            sex: candidate.value.sex,
            email: candidate.value.email,
            telephone: candidate.value.telephone,
            birthday: candidate.value.birthday,
        },
        success: (result:string) => {
            const resp = JSON.parse(result);
            if(resp.error_message === 'success')    ElMessage({message: "更新成功", type: 'success'})
            else    ElMessage.error(resp.error_message);
        }
    })
}
 // eslint-disable-next-line
function setHeadImage(response: any, uploadFile: UploadFile, uploadFiles: UploadFiles) {
    if (uploadFile.raw instanceof Blob) {
        const reader = new FileReader();
        reader.onload = (event)  =>  {
            const fileContent = event.target?.result as string | ArrayBuffer | null;
            if(typeof fileContent === 'string')    headImage.value = fileContent as string;
        }
        reader.readAsDataURL(uploadFile.raw);
    }
}

function handleBeforeUpload(rawFile: UploadRawFile)   {
    const maxSize = 1 * 1024 * 1024; // 1MB
    if (rawFile.size > maxSize) {   //  size的单位是B
        ElMessage.error("上传文件大小不能超过 1MB");
        return false;
    }
    return true; // 允许上传
}
getInformation();
useUserStore().getHeadImg((imgString)=>{
  const fileObj =  useUserStore().base64toFile(imgString, "file");
  const reader = new FileReader();
  reader.readAsDataURL(fileObj);
  reader.onload = (event) => {
    const fileContent = event.target?.result as string | ArrayBuffer | null;
    if(typeof fileContent === 'string')    headImage.value = fileContent as string;
  };
})
</script>

<style scoped>
.container {
    background-color: white;
    width: 50%;
    height: 440px;
    margin: auto auto;
}
.content-wrapper{
    display: flex;
    align-items: center;
}
.display-heading {
    width: 50px;
    height: 50px;
}
.display-heading:hover {
    cursor: pointer;
}
.text-fullname {
    display: inline
}
.font-style {
    font-size: 20px;
}
.input-msg {
    width: 220px;
}
</style>