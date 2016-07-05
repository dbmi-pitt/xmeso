select count(*) from xmeso_visit_dimension;
select count(*) from xmeso_concept_dimension;
select count(*) from xmeso_provider_dimension;

delete from xmeso_patient_dimension;
delete from xmeso_visit_dimension;
delete from xmeso_provider_dimension;
delete from xmeso_concept_dimension;
delete from xmeso_observation_fact;

select distinct(patient_num) as pnum from xmeso_observation_fact where sourcesystem_cd = 'Xmeso' order by pnum;
select distinct(patient_num) as pnum from xmeso_observation_fact where sourcesystem_cd = 'GoldEtl' order by pnum;

select * from xmeso_observation_fact where sourcesystem_cd = 'Xmeso';
select * from xmeso_observation_fact where sourcesystem_cd = 'GoldEtl';


