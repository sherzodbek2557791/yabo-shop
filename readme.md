https://preview.pro.loacg.com/account/center


##database Postgres

####Dumping/Restoring:
pg_dump -h localhost -U yabo_user -F c -f ./yabo_shop.tar.gz yabo_shop wA28CB$L
pg_restore --no-owner -h localhost -U postgres -F c -d postgres yabo_shop.tar.gz

docker exec -it <mycontainer> bash
docker cp <dir/file> <mycontainer>:<dir/file>


docker exec -it c38cebcf8740 bash
docker cp ./Workspace/OwnProjects/shopWorkspace/yabo_dumps/ c38cebcf8740:/home/