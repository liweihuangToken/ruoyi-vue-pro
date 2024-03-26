<template>
	<view class="container" style="background-color: #fff;">
		<viewTop ref="VT" :scrollTop="top"></viewTop>
		<uni-section title="条件组" type="line" padding>
			<view class="example-body">
				<view class="tag-view" v-for="(item, index) in sizeList" :key="index">
					<uni-tag :text="item" :type="isSelected(item) ? 'primary' : 'default'" @click="toggleSelect(item)"/>
				</view>
			</view>
			<view class="example-body">
				<view class="tag-view" v-for="(item2, index2) in this.getDictDatas(DICT_TYPE.DISTRIBUTION_ORDER_STATUS)" :key="index2">
					<uni-tag :text="item2.label" :type="isSelected2(item2.value) ? 'primary' : 'default'" @click="toggleSelect2(item2.value)"/>
				</view>
			</view>
			<view class="right-aligned-text">
				<text>共找到<span style="font-weight: bold; color: red; font-size: 16px;">{{ count }}</span>个订单</text>
			</view>
		</uni-section>
		<uni-list :border="true" v-for="(item, index) in swipeList" :key="index">
			<uni-card :cover="item.orderGoodsPictureUrl" @click="onClick" >
				<!-- <text class="uni-body"> -->
				<view>
					<uni-table>
						<uni-tr>
							<uni-td style="font-weight: bold;min-width: 55px;">尺码:</uni-td>
							<uni-td style="font-weight: bold; color: red; font-size: 20px;min-width: 85px;">{{ item.size }}</uni-td>
							<uni-td style="font-weight: bold;;min-width: 75px;">备注:</uni-td>
							<uni-td>{{ item.note == null?'无': item.note}}</uni-td>
						</uni-tr>
						<uni-tr>
							<uni-td style="font-weight: bold;">客户:</uni-td>
							<uni-td>{{ item.downstreamName }}</uni-td>
							<uni-td style="font-weight: bold;">供货商:</uni-td>
							<uni-td>{{ item.upstreamName }}（{{ item.engPrice }})</uni-td>
						</uni-tr>
					</uni-table>
					<uni-table>
						<uni-tr>
							<uni-td style="font-weight: bold;">状态:</uni-td>
							<uni-td>{{ getDictDataLabel(DICT_TYPE.DISTRIBUTION_ORDER_STATUS, item.orderStatus) }}</uni-td>
						</uni-tr>
						<uni-tr>
							<uni-td style="font-weight: bold;">编码:</uni-td>
							<uni-td style="font-weight: bold; color: black; font-size: 16px;">{{ item.orderCode }}</uni-td>
						</uni-tr>
					</uni-table>
				</view>
				<!-- </text> -->
				<view slot="actions" class="card-actions">
					<view v-if="item.orderStatus == 0" class="card-actions-item" @click="actionsClick(item)">
						<uni-icons type="checkmarkempty" size="18" color="#999"></uni-icons>
						<text class="card-actions-item-text">入库</text>
					</view>
					<view v-if="item.orderStatus == 0" class="card-actions-item" @click="actionsClickToNotStock(item)">
						<uni-icons type="closeempty" size="18" color="#999"></uni-icons>
						<text class="card-actions-item-text">未到货</text>
					</view>
					<view v-if="item.orderStatus != 0" class="card-actions-item" @click="actionsClickToRegister(item)">
						<uni-icons type="refreshempty" size="18" color="#999"></uni-icons>
						<text class="card-actions-item-text">重置为登记</text>
					</view>
				</view>
			</uni-card>
		</uni-list>
		<view class="uni-padding-wrap uni-common-mt">
		<view class="button-sp-area">
		<button v-show="swipeList.length > 0 && selectedTags2[0] == 0" 
			type="warn" 
			plain="true" 
			class="warn-button-body"
			@click="oneClickToNotReceived()">一键未到货
		</button>
		</view>
		</view>
		<view>
			<uni-popup ref="popup" type="dialog">
				<uni-popup-dialog :content="messageText" 
				cancelText="关闭" 
				confirmText="确认" 
				:duration="2000" 
				:before-close="true" 
				@close="dialogClose" 
				@confirm="dialogConfirm">
				</uni-popup-dialog>
			</uni-popup>
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	import viewTop from "./view-top/view-top";
	import { getTodaySupplierOrderList,
			updateOrderStatusToPutStorage,
			updateOrderStatusToNotStock,
			updateOrderStatusToRegister,
			updateOrdersStatusToNotReceived
	} from "@/api/orders/orders";
	import { DICT_TYPE, getDictDataLabel, getDictDatas } from "@/utils/dict";
	
	export default {
		components: { viewTop },
		data() {
			return {
				top:0,
				type: "default",
				inverted: false,
				upstreamAlias: "",
				messageText: "",
				swipeList: [],
				sizeList: [],
				selectedTags: [],
				orderItem: {},
				orderType: 0,
				msgType: "success",
				selectedTags2: [],
				count: 0,
				// cover: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/shuijiao.jpg',
				// avatar: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png',
				extraIcon:{
					color: '#4cd964',
					size: '22',
					type: 'gear-filled'
				}
			}
		},
		onLoad(options) {
		  //  this.selectedTags2[0] = "0";
		  this.upstreamAlias = options.upstreamAlias;
		  this.getTodaySupplierOrderList(options);
		},
		onPullDownRefresh() {
		    // 处理下拉刷新的逻辑
			// this.selectedTags2 = [];
			// this.selectedTags = [];
			const options = {};
			this.getTodaySupplierOrderList(options);
		    // 例如：停止下拉刷新
		    uni.stopPullDownRefresh();
		},
		onPageScroll(e) {
			 //调用子组件方法
			this.$refs.VT.topData(e.scrollTop);
		},
		setType() {
			let types = ["default", "primary", "success", "warning", "error"];
			let index = types.indexOf(this.type);
			types.splice(index, 1);
			let randomIndex = Math.floor(Math.random() * 4);
			this.type = types[randomIndex];
		},
		methods: {
			onClick(e){
				console.log(e)
			},
			actionsClick(item){
				this.orderItem = item;
				this.orderType = 1;
				this.messageText = `请您确认是否对 ${item.orderCode} 订单进行“入库”操作？`
				this.$refs.popup.open('center')
			},
			actionsClickToNotStock(item){
				this.orderItem = item;
				this.orderType = 2;
				this.messageText = `请您确认是否对 ${item.orderCode} 订单进行“未到货”操作？`
				this.$refs.popup.open('center')
			},
			actionsClickToRegister(item){
				this.orderItem = item;
				this.orderType = 3;
				this.messageText = `请您确认是否对 ${item.orderCode} 订单进行重置为“登记”操作？`
				this.$refs.popup.open('center')
			},
			dialogClose() {
				this.$refs.popup.close()
			},
			dialogConfirm() {
				if(this.orderType == 1){
					updateOrderStatusToPutStorage(this.orderItem).then(response => {
						this.messageText = `操作成功`
						this.$refs.message.open()
						this.$refs.popup.close()
						const options = {};
						this.getTodaySupplierOrderList(options);
					})
				}
				if(this.orderType == 2){
					updateOrderStatusToNotStock(this.orderItem).then(response => {
						this.messageText = `操作成功`
						this.$refs.message.open()
						this.$refs.popup.close()
						const options = {};
						this.getTodaySupplierOrderList(options);
					})
				}
				if(this.orderType == 3){
					updateOrderStatusToRegister(this.orderItem).then(response => {
						this.messageText = `操作成功`
						this.$refs.message.open()
						this.$refs.popup.close()
						const options = {};
						this.getTodaySupplierOrderList(options);
					})
				}
				if(this.orderType == 4){
					const options = {};
					options.upstreamAlias = this.upstreamAlias;
					updateOrdersStatusToNotReceived(this.upstreamAlias).then(response => {
						if(response.data > 0) {
							this.messageText = `操作成功`
							this.$refs.message.open()
							this.$refs.popup.close()
							const options2 = {};
							this.getTodaySupplierOrderList(options2);
						} else {
							this.messageText = `操作失败`
							this.$refs.message.open()
						}
						
					})
				}
			},
			isSelected(tag) {
			  return this.selectedTags.includes(tag);
			},
			isSelected2(tag) {
			  return this.selectedTags2.includes(tag);
			},
			toggleSelect(tag) {
			  this.selectedTags = [];
			  const options = {};
			  this.selectedTags.push(tag);
			  this.getTodaySupplierOrderList(options);
			},
			toggleSelect2(tag) {
			  this.selectedTags2 = [];
			  this.selectedTags2.push(tag);
			  const options = {};
			  this.getTodaySupplierOrderList(options);
			},
			
			goToTop() {
			      this.toView = 'top';
			},
			getTodaySupplierOrderList(options) {
				options.upstreamAlias = this.upstreamAlias;
				if(this.selectedTags && this.selectedTags.length > 0){
					options.size = this.selectedTags[0] == '全部'? '': this.selectedTags[0];
				}
				if(this.selectedTags2 && this.selectedTags2.length > 0){
					options.orderStatus = this.selectedTags2[0];
				}
				getTodaySupplierOrderList(options).then(response => {
					this.swipeList = response.data[0].orderDetailInfoFacingObjectRespVOList;
					if(!(this.sizeList.length > 0)){
						this.sizeList = response.data[0].sizeList;
						this.sizeList.push('全部');
					}
					this.count = this.swipeList.length;
				})
			},
			oneClickToNotReceived() {
				this.orderType = 4;
				this.messageText = `请您确认是否对供货商“${this.upstreamAlias}”所属的订单状态为“登记”的所有订单变更为“未到货”？`
				this.$refs.popup.open('center')
			}
		}
	}
</script>

<style lang="scss">

	.container {
		overflow: hidden;
		justify-content: center; /* 水平居中 */
		  align-items: center; /* 垂直居中 */
	}

	.custom-cover {
		flex: 1;
		flex-direction: row;
		position: relative;
	}

	.cover-content {
		position: absolute;
		bottom: 0;
		left: 0;
		right: 0;
		height: 40px;
		background-color: rgba($color: #000000, $alpha: 0.4);
		display: flex;
		flex-direction: row;
		align-items: center;
		padding-left: 15px;
		font-size: 14px;
		color: #fff;
	}

	.card-actions {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
		height: 45px;
		border-top: 1px #eee solid;
	}
	.card-actions-item {
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	.card-actions-item-text {
		font-size: 12px;
		color: #666;
		margin-left: 5px;
	}
	.cover-image {
		flex: 1;
		width: 100%;
		// height: 150px;
		margin: 0;
	}
	.no-border {
		border-width: 0;
	}
	.example-body {
		/* #ifndef APP-PLUS-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: flex-start;
		align-items: flex-end;
		flex-wrap: wrap;
		margin-bottom: 15px;
		align-items: center;
	}
	
	.tag-view {
		margin-left: 20px;
		// margin-right: 15px;
		margin-top: 20px;
		//margin-top: 20px;
	}
	
	.right-aligned-text {
	    display: flex;
	    justify-content: flex-end;
		margin-right: 10px;
		text-decoration: underline;
	}
	
	/deep/ .uni-table-loading {
		display: none; 
	}
	
	.warn-button-body {
		width: 50%; 
		margin-top: 15px; 
		margin-bottom: 15px;
	}
</style>