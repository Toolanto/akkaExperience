
--- COME CAMBIARE I'IP di DEFAULT di DOCKER ---

https://docs.docker.com/engine/userguide/networking/default_network/custom-docker0/
https://support.zenoss.com/hc/en-us/articles/203582809-How-to-Change-the-Default-Docker-Subnet


Creare il file /etc/docker/docker.json con il contenuto qui sotto

{
  "bip": "192.168.1.5/24",
  "fixed-cidr": "10.20.0.0/16",
  "fixed-cidr-v6": "2001:db8::/64",
  "mtu": 1500,
  "default-gateway": "10.20.1.1",
  "default-gateway-v6": "2001:db8:abcd::89",
  "dns": ["10.20.1.2","10.20.1.3"]
}

poi eseguire i seguenti comandi da root

service docker stop
iptables -t nat -F POSTROUTING
ifconfig | grep docker0 -A 1 | grep inet | awk '{print $2}' | cut -c 6-20
e segnarsi l'ip

ip link set dev docker0 down
ip addr del 172.17.0.1/16 dev docker0 (dove 172.17.42.1 è l'ip ottenuto prima)
ip addr add 192.168.5.1/24 dev docker0
ip link set dev docker0 up

Per verificare che tutto è corretto eseguire
ip addr show docker0

e si dovrebbe otterenere un output del tipo
6: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default
    link/ether 02:42:6b:83:59:7a brd ff:ff:ff:ff:ff:ff
    inet 192.168.5.1/24 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:6bff:fe83:597a/64 scope link
       valid_lft forever preferred_lft forever


service docker start
