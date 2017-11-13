FROM centos

USER root

RUN yum -y install java-1.8.0-openjdk-devel && curl https://bintray.com/sbt/rpm/rpm | tee /etc/yum.repos.d/bintray-sbt-rpm.repo && yum -y install sbt bc

COPY testbed /root/testbed

WORKDIR /root/testbed