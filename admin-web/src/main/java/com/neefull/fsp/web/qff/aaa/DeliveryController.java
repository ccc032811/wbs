/*
package com.neefull.fsp.web.qff.aaa;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.aaa.Delivery;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.aaa.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


*/
/**
 * 到货QFF操作
 *
 * @Author: chengchengchu
 * @Date: 2019/12/6  18:50
 *//*


@RestController
@RequestMapping("/delivery")
public class DeliveryController extends BaseController {


    @Autowired
    private IDeliveryService deliveryService;


    */
/**新增到货QFF
     * @param delivery
     * @return
     * @throws FebsException
     *//*

    @PostMapping("/add")
    public FebsResponse addDelivery(Delivery delivery) throws FebsException {
        Integer count = deliveryService.saveDelivery(delivery);
        if(count!=1){
            throw new FebsException("新增到货QFF失败");
        }
        return new FebsResponse().success();
    }

    */
/**修改到货QFF
     * @param delivery
     * @return
     * @throws FebsException
     *//*


    @PostMapping("/edit")
    public FebsResponse editDelivery(Delivery delivery) throws FebsException {
        Integer count = deliveryService.editDelivery(delivery);
        if(count!=1){
            throw new FebsException("修改到货QFF失败");
        }
        return new FebsResponse().success();
    }

    */
/**查询到货QFF
     * @param query
     * @return
     *//*


    @GetMapping("/list")
    public FebsResponse getDeliveryPage(Query query){
        IPage<Delivery> page = deliveryService.getDeliveryPage(query);
        Map<String, Object> dataTable = getDataTable(page);
        return new FebsResponse().success().data(dataTable);
    }

    */
/**删除到货QFF
     * @param number
     * @return
     * @throws FebsException
     *//*


    @GetMapping("/deleteDelivery")
    public FebsResponse deleteDelivery(@RequestParam("number")String number) throws FebsException {
        Integer count = deliveryService.deleteDelivery(number);
        if(count!=1){
            throw new FebsException("删除到货QFF失败");
        }
        return new FebsResponse().success();
    }

    */
/**查询到货QFF
     * @param number
     * @return
     * @throws FebsException
    *//*


    @GetMapping("/queryDelivery")
    public FebsResponse queryDeliveryByNumber(@RequestParam("number")String number) throws FebsException {
        Delivery delivery = deliveryService.queryDeliveryByNumber(number);
        if (delivery == null) {
            throw new FebsException("查询到货QFF失败");
        }
        return new FebsResponse().success().data(delivery);
    }

}
*/
