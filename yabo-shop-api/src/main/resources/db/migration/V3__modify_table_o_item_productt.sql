-- Modify table
alter table o_item_product add constraint o_item_product_uk1 unique (o_group_id, product_id);