package uz.kvikk.yabo.service.impl

import org.jooq.DSLContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import uz.kvikk.yabo.model.transport.ProductResponse
import uz.kvikk.yabo.service.ProductService
import uz.kvikk.yabo.utils.TripleFunction

@Service
class ProductServiceImpl(val dsl: DSLContext) : ProductService {

    override fun listPagingFiltered(pageable: Pageable, productStatus: String, categoryCode: String?): Page<ProductResponse> {
        return this.pageFiltered(pageable, mapOf<TripleFunction<MutableList<Any>, MutableList<Any>, Int, String>, Any>(
                TripleFunction { selectParams: MutableList<Any>,
                                 countParams: MutableList<Any>,
                                 i: Int ->
                    selectParams.add(productStatus)
                    countParams.add(productStatus)
                    return@TripleFunction "and p.product_status = {$i}"
                } to productStatus,
                TripleFunction { selectParams: MutableList<Any>,
                                 countParams: MutableList<Any>,
                                 i: Int ->
                    if (StringUtils.isEmpty(categoryCode!!)) return@TripleFunction ""
                    selectParams.add(categoryCode)
                    countParams.add(categoryCode)

                    return@TripleFunction """
                        and p.id in (select cp.product_id from category c
                            left join category_product cp on c.id = cp.category_id where c.code={$i})
                    """.trimIndent()
                } to categoryCode!!
        ))
    }

    override fun listPaging(pageable: Pageable, productStatus: String): Page<ProductResponse> {
        return this.pageFiltered(pageable, mapOf<TripleFunction<MutableList<Any>, MutableList<Any>, Int, String>, Any>(
                TripleFunction { selectParams: MutableList<Any>,
                                 countParams: MutableList<Any>,
                                 i: Int ->
                    selectParams.add(productStatus)
                    countParams.add(productStatus)
                    return@TripleFunction "and p.product_status = {$i}"
                } to productStatus
        ))
    }

    override fun pageFiltered(pageable: Pageable, filters: Map<TripleFunction<MutableList<Any>, MutableList<Any>, Int, String>, Any>): Page<ProductResponse> {
        var selectParams = mutableListOf<Any>()
        var countParams = mutableListOf<Any>()
        var select = "$SELECT "
        var count = "$TOTAL "
        var i = 0
        for (filter in filters) {
            var function = filter.key
            val condition = function.apply(selectParams, countParams, i++)
            if (StringUtils.isEmpty(condition)) continue

            select += condition
            count += condition
        }

        select += " OFFSET {${i++}} ROWS FETCH NEXT {${i++}} ROWS ONLY "
        selectParams.add(pageable.offset)
        selectParams.add(pageable.pageSize)

        val list = dsl.fetch(select, *selectParams.toTypedArray()).into(ProductResponse::class.java)
        val total = dsl.fetchOne(count, *countParams.toTypedArray()).into(Long::class.java)
        return PageImpl(list, pageable, total)
    }

    companion object {
        var SELECT: String = """
           select p.code,
                   p.title,
                   p.description,
                   p.price,
                   p.currency,
                   p.image,
                   p.start_date
            from product p
            where 1=1
        """.trimIndent()

        var TOTAL: String = """
            select count(p.id)
            from product p
            where 1=1
        """.trimIndent()
    }

}