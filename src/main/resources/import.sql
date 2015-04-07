insert into country(code,name,latitude,longitude) values('DE','Germany',51.1642292,10.4541194);
insert into country(code,name,latitude,longitude) values('GB','United Kingdom',55.3617609,-3.4433238);
insert into country(code,name,latitude,longitude) values('FR','France',0,0);
insert into country(code,name,latitude,longitude) values('IT','Italy',0,0);
insert into country(code,name,latitude,longitude) values('NO','Norway',0,0);
insert into country(code,name,latitude,longitude) values('BE','Belgium',0,0);
insert into country(code,name,latitude,longitude) values('NL','Netherlands',0,0);
insert into country(code,name,latitude,longitude) values('SE','Sweden',0,0);
insert into country(code,name,latitude,longitude) values('FI','Finland',0,0);
insert into country(code,name,latitude,longitude) values('RU','Russia',0,0);
insert into country(code,name,latitude,longitude) values('US','United States of America',0,0);


insert into un_location(code,name,LATITUDE,LONGITUDE) values('DEHAM', 'Hamburg',53.558572,9.9278215);
insert into un_location(code,name,LATITUDE,LONGITUDE) values('DEBRE', 'Bremen',3.558572,9.9278215);
insert into un_location(code,name,LATITUDE,LONGITUDE) values('DEBER', 'Berlin',3.558572,9.9278215);
insert into un_location(code,name,LATITUDE,LONGITUDE) values('DEWED', 'Wedel',53.5884834,9.7029192);
insert into un_location(code,name,LATITUDE,LONGITUDE) values('FRPAR','Paris',3.558572,9.9278215);
insert into un_location(code,name,LATITUDE,LONGITUDE) values('GBLON','London',3.558572,9.9278215);


insert into city(country_code, name) values('DE','Hamburg');
insert into city(country_code, name) values('DE','Bremen');
insert into city(country_code, name) values('DE','Berlin');
insert into city(country_code, name, latitude,longitude) values('DE','Wedel',53.5884834,9.7029192);
insert into city(country_code, name, latitude,longitude) values('DE','Pinneberg',53.6463282,9.7961386);
insert into city(country_code, name) values('DE','Holm');
insert into city(country_code, name) values('DE','Haseldorf');
insert into city(country_code, name) values('DE','Hetlingen');

insert into city(country_code, name) values('GB','London');

insert into city(country_code, name,latitude,longitude) values('DE','Emden',53.3644256,7.1553541);

insert into mapping(location_id, city_id) values(1,1);
insert into mapping(location_id, city_id) values(2,2);
insert into mapping(location_id, city_id) values(3,3);
insert into mapping(location_id, city_id) values(4,4);
insert into mapping(location_id, city_id) values(4,5);
insert into mapping(location_id, city_id) values(4,6);
insert into mapping(location_id, city_id) values(4,7);
insert into mapping(location_id, city_id) values(4,8);