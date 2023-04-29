#!/bin/bash
# 获得所有 以 shop- 开头的目录
dirs=($(find . -maxdepth 1 -type d -name 'shop-*' -printf '%f\n'))
# 遍历上述目录
for dir in "${dirs[@]}"; do
    # 如果目录不是 shop-third-party 和 shop-system
    if [ "$dir" != "shop-third-party" ] && [ "$dir" != "shop-system" ]; then
        # 进入子目录中
        cd "$dir"
        # 处理当前目录下的子目录
        subdirs=($(find . -maxdepth 1 -type d -not -name "." -printf '%f\n'))
        # 遍历子目录
        for subdir in "${subdirs[@]}"; do
            if [ "$subdir" != "src" ] && [ "$subdir" != "target" ]; then
                # 把子目录当做项目名称
                project_name=$(basename "$subdir")
                echo "Building and pushing $project_name..."
                mvn spring-boot:build-image -Dspring-boot.build-image.imageName=hanxuelei/"${project_name}":v1
                docker push hanxuelei/"${project_name}":v1
            fi
            # 在这里添加您的代码，处理每个子目录
        done
        cd ..
    fi
done
# 处理特殊的两个
for d in ./shop-system/ ./shop-third-party/; do
    cd "$d"
    echo "Building and pushing $(basename "$d")..."
    mvn spring-boot:build-image -Dspring-boot.build-image.imageName=hanxuelei/"$(basename "$d")":v1
    docker push hanxuelei/"$(basename "$d")":v1
    cd ..
done

# #!/bin/bash

# function build_and_push {
#     for d in "$1"*/ ; do
#         doc_name=$(basename "$d")
#         if [[ -d "$d" && "$doc_name" =~ ^shop-$ ]]; then
#             echo "$doc_name"
#             cd "$d"
#             project_name=$(basename "$d")
#             if [[ "$project_name" =~ ^shop-(?!system|third-party)[a-zA-Z]*-(rpc|api)$ ]]; then
#                 echo "Building and pushing $project_name..."
#                 # mvn spring-boot:build-image -Dspring-boot.build-image.imageName=hanxuelei/"${project_name}":v1
#                 # docker push hanxuelei/"${project_name}":v1
#             else
#                 echo "Skipping $project_name..."
#             fi
#             cd ..
#         elif [[ -d "$d" ]]; then
#             # 如果是子目录，则递归构建子目录中的项目
#             build_and_push "$d"
#         fi
#     done
# }

# build_and_push "./"

# for d in ./shop-system/ ./shop-third-party/; do
#     cd "$d"
#     echo "Building and pushing $(basename "$d")..."
#     # mvn spring-boot:build-image -Dspring-boot.build-image.imageName=hanxuelei/"$(basename "$d")":v1
#     # docker push hanxuelei/"$(basename "$d")":v1
#     cd ..
# done

# mvn spring-boot:build-image \
#          -Ddocker.publishRegistry.username=hanxuelei \
#          -Ddocker.publishRegistry.password=dckr_pat_nbrMtePv6y2R4n-krmy9Bbb1EL0 \
#          -Dspring-boot.build-image.publish=true \
#          -Dspring-boot.build-image.imageName=hanxuelei/



# # 定义 publish 的地址
# #   -Ddocker.publishRegistry.url=https://docker.io \
# # password: dckr_pat_nbrMtePv6y2R4n-krmy9Bbb1EL0
# mvn spring-boot:build-image -Dspring-boot.build-image.imageName=hanxuelei/