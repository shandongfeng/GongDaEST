<template>
    <div>
        <br/>
        <br/>   
        <!--form-->
        <div class="button-form">
            <!--对话框-->    
            <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose"
        >                 
                <!--输入框-->
<!-- ref="form": 这个属性指定了el-form的引用名称，可以在Vue组件内通过$refs访问该el-form实例。 
     :model="form": 这个属性将表单数据和data中的form对象进行双向绑定。这意味着表单输入的值将同步更新到form对象，并且form对象的值的变化也会反映到表单上。-->
                <el-form ref="form" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="姓名" prop="name">
                        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>             
                    </el-form-item>
                    <!--性别-->
                    <el-form-item label="性别" prop="gender" disabled = true >
                        <el-radio v-model="form.gender" label="man">男</el-radio>
                        <el-radio v-model="form.gender" label="woman">女</el-radio>
                    </el-form-item>
                    <!--日期-->
                    <el-form-item label="出生日期" prop="bornDate">
                        <el-date-picker
                            v-model="form.bornDate"
                            type="date"
                            placeholder="选择日期"
                        ></el-date-picker>
                    </el-form-item>
                    <!--地址-->
                    <el-form-item label="地址" prop="address">
                        <el-input v-model="form.address" placeholder="请输入地址"></el-input>             
                    </el-form-item>
                </el-form>
                <!--底部栏按钮-->   
                <span slot="footer" class="dialog-footer">
                    <el-button @click="cancel">取 消</el-button>
                    <el-button type="primary" @click="submit">确 定</el-button>
                </span>
                                
            </el-dialog>
            <!--新增按钮-->    
            <el-row>           
                <el-button type="primary" @click="dialogVisible = true" round>新 增</el-button>        
            </el-row>
    
        </div>
        <br/>
        
        <!--table-->
        <el-table
        :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: auto">
        <!--表头-->
            <!--id自增-->
            <el-table-column type="index" label="序号"> </el-table-column>
            <el-table-column label="姓名" prop="name"></el-table-column>
            <el-table-column label="性别" prop="gender"> </el-table-column>
            <el-table-column label="出生日期" prop="bornDate" :formatter="formatDate"></el-table-column>
            <el-table-column label="地址" prop="address"> </el-table-column>  
            
            <el-table-column align="right">
                <template slot="header">
                <!--搜索框-->
                <el-input
                    v-model="search"
                    size="mini"
                    placeholder="输入关键字搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>
  
<script>

export default {
    data() {    //声明全局变量
        return {
            //按编辑按钮弹出对话框
            isClickEdit: false,
            //当前表格索引
            currentIndex: 0,
            //按新增按钮弹出对话框
            dialogVisible: false,   
            //储存form原先的数据
            oldform: {     
                name: '',
                gender: '',
                bornDate: '',
                address: '',
            },
            //与上面的ref和model的form中的标签进行交互
            form: {     
                name: '',
                gender: '',
                bornDate: '',
                address: '',
            },
            //form输入规则
            rules: {
                //四项信息必须填写，否则报错
                name:[{required: true, message: "请输入姓名"}],
                gender:[{required: true, message: "请选择性别"}],
                bornDate:[{required: true, message: "请选择出生日期"}],
                address:[{required: true, message: "请输入家庭地址"}],
            },
            //存储table数据
            tableData: [],
            //临时储存搜索框的信息，刷新会失效
            search: ''  
        };
    },

    mounted() {
        // 从本地存储中获取数据
        const savedData = localStorage.getItem('tableData');
        if (savedData) {
            console.log("————table————导入本地储存数据");
            const msg = JSON.parse(savedData);
            msg.forEach((item)=>{
                this.tableData.push(item);
            });
        }
    },

    
    methods: {
        handleEdit(index, row) {
            console.log("————table————当前行信息;",index, row);
            this.currentIndex = index;  //获取当前行索引
            this.dialogVisible = true;  //对话框打开
            this.isClickEdit = true;    //判断是否点击编辑按钮，方便再submit里做判断
            this.form = row;            //row === this.tableData.at(index);    
            this.oldform = {...row};   //保存原先数据，方便与新form作比较
            //console.log("oldform = ",this.oldform);
        },
        handleDelete(index, row) {
            console.log(index, row);
        },
        //日期格式
        formatDate(row) {
            const date = new Date(row.bornDate);
            const year = date.getFullYear();
            let month = date.getMonth() + 1;
            if (month < 10) {
            month = '0' + month;
            }
            let day = date.getDate();
            if (day < 10) {
            day = '0' + day;
            }
            return `${year}-${month}-${day}`;
        },
        //点击form确定按钮
        submit(){
            this.$refs.form.validate((valid) =>{
                //如果form非空
                if(valid)
                {
                    //如果按钮是新增
                    if(!this.isClickEdit){
                        //console.log(this.form);  
                        //添加form表单到table,如果不新建form，那么新增的信息都会保存到原有的form，此时form相当于是一个地址，会导致添加多个form都是重复的
                        const newForm = {
                            name: this.form.name,
                            gender: this.form.gender,
                            bornDate: this.form.bornDate,
                            address: this.form.address
                        };
                        this.tableData.unshift(newForm);    //新增数据在table上方 
                        //this.tableData.push(newForm);   //新增数据在table下方 

                        // 保存到本地存储
                        localStorage.setItem('tableData', JSON.stringify(this.tableData));
                       
                    }else{  //如果按钮是编辑
                        let storedData = localStorage.getItem('tableData');
                        let newData = JSON.parse(storedData);
                        if(this.form !== newData[this.currentIndex]){
                            newData[this.currentIndex] = this.form;
                            //localStorage.removeItem('tableData');     //不用删除直接替换即可
                            let updatedData = JSON.stringify(newData);
                            localStorage.setItem('tableData', updatedData);
                            console.log("————table————成功修改信息并储存到本地")
                        }else{
                            console.log("————table————没有数据修改")
                        }
                           
                    }
                    this.handleClose();
                }
            });
        },
        //点击form取消按钮
        cancel() {      
            this.handleClose();         
        },
        //点击确定或取消按钮之后隐藏dialog
        handleClose() { 
            console.log("form关闭后 :清空form，再隐藏form");
            //编辑界面：如果form改变但是没有提交时，需要还原form的内容
            if(this.isClickEdit && this.form !== this.oldform){ //this.form === this.tableData[this.currentIndex]
                console.log("————cancel按钮————form虽然改变但是没有提交，所以还原数据");
                this.tableData[this.currentIndex] = this.oldform;
            }
            this.form = {};
            this.dialogVisible=false;
            this.isClickEdit=false;
            
        },
        
    },
    
}
</script>