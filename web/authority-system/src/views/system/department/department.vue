<template>
  <el-main>
    <!-- 条件查询区域 -->
    <el-form
      v-model="searchModel"
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item>
        <el-input
          v-model="searchModel.departmentName"
          placeholder="请输入部门名称"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search()" icon="icon-search"
          >查询</el-button
        >
        <el-button icon="el-icon-refresh-right">重置</el-button>
        <el-button type="success" icon="el-icon-plus" @click="openAddWindow()" v-if="hasPermission('sys:department:add')"
          >新增</el-button
        >
      </el-form-item>
    </el-form>

    <!-- 
      数据树形表格 
      data: 表格数据
      stripe: 表格斑马线
      row-key: 行属性的key, 用来优化Table的渲染
      default-expand-all: 默认展开树形表格的数据
      tree-props: 树形表格配置属性选项
    -->
    <el-table
      :data="tableData"
      style="width: 100%; margin-bottom: 20px"
      row-key="id"
      border
      stripe
      lazy
      default-expand-all
      :tree-props="{ children: 'children' }"
    >
      <!-- prop: 填写数据的属性名称  -->
      <el-table-column prop="departmentName" label="部门名称">
      </el-table-column>
      <el-table-column prop="parentName" label="所属部门"> </el-table-column>
      <el-table-column prop="phone" label="部门电话"> </el-table-column>
      <el-table-column prop="address" label="部门地址"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            icon="el-icon-edit-outline"
            type="primary"
            @click="handleEdit(scope.row)"
            v-if="hasPermission('sys:department:edit')"
            >编辑</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-delete-solid"
            type="danger"
            @click="handleDelete(scope.row)"
            v-if="hasPermission('sys:department:delete')"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加和修改窗口 -->
    <system-dialog
      :title="deptDialog.title"
      :visible="deptDialog.visible"
      :width="deptDialog.width"
      :height="deptDialog.height"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <!-- 插槽 -->
      <div slot="content">
        <el-form
          :model="dept"
          ref="deptForm"
          :rules="rules"
          label-width="80px"
          :inline="true"
          size="small"
        >
          <el-form-item label="所属部门" prop="parentName">
            <el-input
              v-model="dept.parentName"
              :readonly="true"
              @focus="openSelectParentsWindow()"
            ></el-input>
          </el-form-item>
          <el-form-item label="部门名称" prop="departmentName">
            <el-input v-model="dept.departmentName"></el-input>
          </el-form-item>
          <el-form-item label="部门电话">
            <el-input v-model="dept.phone"></el-input>
          </el-form-item>
          <el-form-item label="部门地址">
            <el-input v-model="dept.address"></el-input>
          </el-form-item>
          <el-form-item label="部门序号">
            <el-input v-model="dept.orderNum"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </system-dialog>

    <!-- 选择所属部门的窗口 -->
    <system-dialog
      :title="parentsDialog.title"
      :visible="parentsDialog.visible"
      :width="parentsDialog.width"
      :height="parentsDialog.height"
      @onClose="onParentsClose()"
      @onConfirm="onParentsConfirm()"
    >
      <div slot="content">
        <!-- 
          highlight-current: 点击树中某节点高亮
          expand-on-click-node: 点击节点文字, 不触发事件(只有点击节点左边图标才触发)
          node-click: 点击节点触发的事件, 如果声明了上一个属性, 那就是点击了节点图标触发的事件
         -->
        <el-tree
          ref="parentsTree"
          :data="parentsDeptList"
          node-key="id"
          :props="defaultProps"
          :highlight-current="true"
          :default-expand-all="true"
          :expand-on-click-node="false"
          empty-text="暂无数据"
          @node-click="handleNodeClick"
        >
          <div class="customer-tree-node" slot-scope="{ node, data }">
            <!-- 判断当前节点子节点长度是否为0 -->
            <span v-if="data.children.length === 0">
              <i class="el-icon-document"></i>
            </span>
            <span v-else @click="changeIcon(data)">
              <!-- icon-class需要你到 阿里巴巴矢量图标网 下载加号和减号的svg图标, 然后放到`/src/icons/`下 -->
              <svg-icon v-if="data.open" icon-class="add"></svg-icon>
              <svg-icon v-else icon-class="sub"></svg-icon>
            </span>
            <span style="margin-left: 3px">{{ node.label }}</span>
          </div>
        </el-tree>
      </div>
    </system-dialog>
  </el-main>
</template>

<script>
// 导入department脚本文件
import departmentApi from "@/api/department";

// 导入自定义的system弹窗组件
import SystemDialog from "@/components/system/SystemDialog.vue";

export default {
  name: "department",

  data() {
    return {
      searchModel: {
        departmentName: "",
      }, // 部门名称
      tableData: [], // 表中部门数据
      deptDialog: {
        // 添加和修改弹窗组件的属性
        title: "", // 弹窗标题
        visible: false, // 是否显示
        width: 560, // 宽度
        height: 170, // 高度
      },
      parentsDialog: {
        // 选择父部门弹窗的属性
        title: "", // 弹窗标题
        visible: false, // 是否显示
        width: 300, // 宽度
        height: 400, // 高度
      },
      dept: {
        // 添加或修改窗口的部门表单
        id: "", // 部门编号
        departmentName: "", // 部门名称
        pid: "", // 父部门id
        parentName: "", // 父部门名称 / 所属部门
        phone: "", // 部门电话
        address: "", // 部门地址
        orderNum: "", // 部门序号
      },
      rules: {
        // 添加或修改窗口的表单验证规则
        parentName: [
          { required: true, message: "请选择所属部门", trigger: "change" }, // 使用change触发
        ],
        departmentName: [
          { required: true, message: "请选择部门名称", trigger: "blur" }, // 使用blur触发
        ],
      },
      parentsDeptList: [], // 父部门列表
      defaultProps: {
        // 父部门树形组件默认属性值
        children: "children",
        label: "departmentName",
      },
    };
  },
  components: {
    SystemDialog,
  },
  // 初始化时调用
  created() {
    this.search();
  },
  methods: {
    /**
     * 查询部门列表
     */
    async search() {
      // 发送查询请求
      let res = await departmentApi.getDepartmentList(this.searchModel);
      // console.log(res);
      // 判断是否成功
      if (res.success) {
        this.tableData = res.data;
      }
    },
    /**
     * 编辑单条数据
     */
    handleEdit(row) {
      // 将要编辑的单行数据 复制 给编辑表单 数据
      this.$objCopy(row, this.dept);

      // 设置窗口标题
      this.deptDialog.title = "编辑部门";
      // 显示窗口
      this.deptDialog.visible = true;
    },
    /**
     * 删除某条数据
     */
    async handleDelete(row) {
      // 查询部门下是否存在子部门或用户
      let res = await departmentApi.checkHasParentDepartment({id:row.id});

      // 判断是否可以删除
      if(!res.success){
        this.$message.warning(res.message);
      }else{
        // 判断是否删除
        let confirm = await this.$myconfirm("确定要删除该数据吗?");
        if(confirm){
          // 发送删除请求
          let res = await departmentApi.deleteDeptById({id:row.id});

          // 判断是否成功
          if(res.success){
            this.$message.success(res.message);
            // 刷新
            this.search();
          }else{
            this.$message.error(res.message);
          }
        }
      }
    },

    /**
     * 打开添加或修改部门弹窗，对弹窗的初始化操作
     */
    openAddWindow() {
      this.$resetForm("deptForm", this.dept);

      this.deptDialog.title = "新增部门";
      this.deptDialog.visible = true;
    },

    /**
     * 添加和修改弹窗的关闭方法
     */
    onClose() {
      // 关闭窗口
      this.deptDialog.visible = false;
    },
    /**
     * 添加和修改弹窗的确定方法
     */
    onConfirm() {
      // 表单验证
      this.$refs.deptForm.validate(async (valid) => {
        // 如果验证通过
        if (valid) {
          let res = null;
          // 判断当前操作是添加还是修改
          // 依据: 当前dept对象的id属性是否为空
          if (this.dept.id === "") {
            // 发送添加请求
            res = await departmentApi.addDept(this.dept);
          }else{
            // 发送修改请求
            res = await departmentApi.updateDept(this.dept);
          }

          // 判断是否成功
          if (res.success) {
            // 提示成功
            this.$message.success(res.message);
            // 刷新数据
            this.search();
          }

          // 关闭窗口
          this.deptDialog.visible = false;
        } else {
          this.$message.error(res.message);
        }
      });
    },

    /**
     * 打开选择父部门弹窗，对弹窗的初始化操作
     */
    async openSelectParentsWindow() {
      // 请求父部门列表
      let res = await departmentApi.getParentsTreeList();
      // console.log(res);

      // 判断是否成功
      if (res.success) {
        this.parentsDeptList = res.data;
      }

      this.parentsDialog.title = "选择所属部门";
      this.parentsDialog.visible = true;
    },

    /**
     * 选择父部门弹窗的关闭方法
     */
    onParentsClose() {
      this.parentsDialog.visible = false;
    },
    /**
     * 选择父部门弹窗的确定方法
     */
    onParentsConfirm() {
      this.parentsDialog.visible = false;
    },
    // 点击父部门树形组件中数据的事件
    handleNodeClick(data) {
      this.dept.pid = data.id;
      this.dept.parentName = data.departmentName;
    },

    /**
     * 选择父部门时，点击部门树左侧的图标, 部门树的图标状态改变
     */
    changeIcon(data) {
      data.open = !data.open;
      this.$refs.parentsTree.store.nodesMap[data.id].expanded = !data.open;
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-tree {
  .el-tree-node {
    position: relative;
    padding-left: 10px;
  }
  .el-tree-node__children {
    padding-left: 20px;
  }
  .el-tree-node :last-child:before {
    height: 40px;
  }
  .el-tree > .el-tree-node:before {
    border-left: none;
  }
  .el-tree > .el-tree-node:after {
    border-top: none;
  }
  .el-tree-node:before,
  .el-tree-node:after {
    content: "";
    left: -4px;
    position: absolute;
    right: auto;
    border-width: 1px;
  }
  .tree :first-child .el-tree-node:before {
    border-left: none;
  }
  .el-tree-node:before {
    border-left: 1px dotted #d9d9d9;
    bottom: 0px;
    height: 100%;
    top: -25px;
    width: 1px;
  }
  .el-tree-node:after {
    border-top: 1px dotted #d9d9d9;
    height: 20px;
    top: 14px;
    width: 24px;
  }
  .el-tree-node__expand-icon.is-leaf {
    width: 8px;
  }
  .el-tree-node__content > .el-tree-node__expand-icon {
    display: none;
  }
  .el-tree-node__content {
    line-height: 30px;
    height: 30px;
    padding-left: 10px !important;
  }
}
::v-deep .el-tree > div {
  &::before {
    display: none;
  }
  &::after {
    display: none;
  }
}
</style>