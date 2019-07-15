UPDATE region SET name_uz_uz = 'ANDIJON VILOYATI', name_ru_ru = 'АНДИЖАНСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Andijon sh.', center_region_name_ru_ru = 'г. Андижан' WHERE soato = '1703';
UPDATE region SET name_uz_uz = 'BUXORO VILOYATI', name_ru_ru = 'БУХАРСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Buxoro sh.', center_region_name_ru_ru = 'г. Бухара' WHERE soato = '1706';
UPDATE region SET name_uz_uz = 'TOSHKENT SHAHRI', name_ru_ru = 'ГОРОД ТАШКЕНТ', center_region_name_uz_uz = null, center_region_name_ru_ru = null WHERE soato = '1726';
UPDATE region SET name_uz_uz = 'XORAZM VILOYATI', name_ru_ru = 'ХОРЕЗМСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Urganch sh.', center_region_name_ru_ru = 'г. Ургенч' WHERE soato = '1733';
UPDATE region SET name_uz_uz = 'Qoraqalpog`iston Respublikasi', name_ru_ru = 'Республика Каракалпакстан', center_region_name_uz_uz = 'Nukus sh.', center_region_name_ru_ru = 'г. Hукус' WHERE soato = '1735';
UPDATE region SET name_uz_uz = 'QASHQADARYO VILOYATI', name_ru_ru = 'КАШКАДАРЬИНСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Qarshi sh.', center_region_name_ru_ru = 'г. Карши' WHERE soato = '1710';
UPDATE region SET name_uz_uz = 'SAMARQAND VILOYATI', name_ru_ru = 'САМАРКАНДСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Samarqand sh.', center_region_name_ru_ru = 'г. Самарканд' WHERE soato = '1718';
UPDATE region SET name_uz_uz = 'FARG`ONA VILOYATI', name_ru_ru = 'ФЕРГАНСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Farg`ona sh.', center_region_name_ru_ru = 'г. Фергана' WHERE soato = '1730';
UPDATE region SET name_uz_uz = 'SIRDARYO VILOYATI', name_ru_ru = 'СЫРДАРЬИНСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Guliston sh.', center_region_name_ru_ru = 'г. Гулистан' WHERE soato = '1724';
UPDATE region SET name_uz_uz = 'JIZZAX VILOYATI', name_ru_ru = 'ДЖИЗАКСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Jizzax sh. ', center_region_name_ru_ru = 'г. Джизак' WHERE soato = '1708';
UPDATE region SET name_uz_uz = 'NAVOIY VILOYATI', name_ru_ru = 'НАВОИЙСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Navoiy sh.', center_region_name_ru_ru = 'г. Навои' WHERE soato = '1712';
UPDATE region SET name_uz_uz = 'NAMANGAN VILOYATI', name_ru_ru = 'НАМАНГАНСКАЯ ОБЛАСТЬ ', center_region_name_uz_uz = 'Namangan sh.', center_region_name_ru_ru = 'г. Наманган' WHERE soato = '1714';
UPDATE region SET name_uz_uz = 'SURXANDARYO VILOYATI', name_ru_ru = 'СУРХАНДАРЬИНСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Termiz sh.', center_region_name_ru_ru = 'г. Термез' WHERE soato = '1722';
UPDATE region SET name_uz_uz = 'TOSHKENT VILOYATI', name_ru_ru = 'ТАШКЕНТСКАЯ ОБЛАСТЬ', center_region_name_uz_uz = 'Toshkent sh.', center_region_name_ru_ru = 'г. Ташкент' WHERE soato = '1727';

alter table region
    drop column center_region_id;