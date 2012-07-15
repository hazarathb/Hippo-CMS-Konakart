package org.onehippo.forge.konakart.site.service.impl;

import com.konakart.al.KKAppEng;
import com.konakart.app.DataDescConstants;
import com.konakart.app.DataDescriptor;
import com.konakart.app.KKException;
import com.konakart.appif.DataDescriptorIf;
import com.konakart.appif.ProductIf;
import com.konakart.appif.ProductsIf;
import com.konakart.bl.ProductMgr;
import org.hippoecm.hst.core.component.HstRequest;
import org.onehippo.forge.konakart.site.service.KKProductService;

public class KKProductServiceImpl extends KKBaseServiceImpl implements KKProductService {

    @Override
    public ProductIf[] fetchNewProducts(HstRequest hstRequest) {
        return fetchNewProducts(hstRequest, ProductMgr.DONT_INCLUDE, false, false, DataDescConstants.MAX_ROWS);
    }

    @Override
    public ProductIf[] fetchNewProducts(HstRequest hstRequest, int categoryId) {
        return fetchNewProducts(hstRequest, categoryId, false, false, DataDescConstants.MAX_ROWS);
    }

    @Override
    public ProductIf[] fetchNewProducts(HstRequest hstRequest, int categoryId, boolean fetchDescription) {
        return fetchNewProducts(hstRequest, categoryId, fetchDescription, false, DataDescConstants.MAX_ROWS);
    }

    @Override
    public ProductIf[] fetchNewProducts(HstRequest hstRequest, int categoryId, boolean fetchDescription, int limit) {
        return fetchNewProducts(hstRequest, categoryId, fetchDescription, false, limit);
    }

    @Override
    public ProductIf[] fetchNewProducts(HstRequest hstRequest, int categoryId, boolean fetchDescription, boolean showInvisible) {
        return fetchNewProducts(hstRequest, categoryId, fetchDescription, false, DataDescConstants.MAX_ROWS);
    }

    @Override
    public ProductIf[] fetchNewProducts(HstRequest hstRequest, int categoryId, boolean fetchDescription,
                                        boolean showInvisible, int limit) {
        KKAppEng kkAppEng = getKKAppEng(hstRequest);

        DataDescriptorIf dataDescriptorIf = new DataDescriptor();
        dataDescriptorIf.setShowInvisible(showInvisible);
        dataDescriptorIf.setFillCustomAttrArray(true);
        dataDescriptorIf.setFillDescription(fetchDescription);
        dataDescriptorIf.setOffset(0);
        dataDescriptorIf.setLimit(limit);
        dataDescriptorIf.setOrderBy(DataDescConstants.ORDER_BY_DATE_ADDED);

        try {
            ProductsIf productsIf = kkAppEng.getEng().getProductsPerCategoryWithOptions(kkAppEng.getSessionId(), dataDescriptorIf, categoryId,
                    true, kkAppEng.getLangId(), kkAppEng.getFetchProdOptions());

            return productsIf.getProductArray();
        } catch (KKException e) {
            log.warn("Failed to retrieve the new products");
        }

        return new ProductIf[0];
    }
}