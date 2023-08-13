FROM mysql:8.0.33

COPY k8s-run.sh /k8s-run.sh
COPY db-init.sql /db-init.sql

RUN chmod 777 /k8s-run.sh
RUN chmod 777 /db-init.sql

ENTRYPOINT ["/k8s-run.sh"]
CMD ["localhost", "23306"]

# docker buildx build --platform linux/arm64 --push -t ojt90902/db-initcontainer:latest .